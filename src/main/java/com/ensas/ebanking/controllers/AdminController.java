package com.ensas.ebanking.controllers;

import com.ensas.ebanking.entities.*;
import com.ensas.ebanking.repositories.*;
import com.ensas.ebanking.services.EmailService;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static com.ensas.ebanking.enumeration.Role.ROLE_ADMIN;
import static com.ensas.ebanking.enumeration.Role.ROLE_AGENT;

@Controller
public class AdminController {

    private BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder();;
    private EmailService emailService;

    @Autowired
    private AgenceRepository agenceRepository;
    @Autowired
    private AdresseRepository adresseRepository;
    @Autowired
    private BanqueRepository banqueRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AgentRepository agentRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private AdminRepository adminRepository;

    @GetMapping("/Admin/index")
    public String afficher(Model model) {
        double solde=banqueRepository.findById(1L).get().getSolde();
        int agents=agentRepository.findByIsActive(true).size();
        int agencesInactif=agenceRepository.findByActive(false).size();
        int agences=agenceRepository.findByActive(true).size();
        int clients=clientRepository.findAll().size();
        model.addAttribute("solde",solde);
        model.addAttribute("agents",agents);
        model.addAttribute("agences",agences);
        model.addAttribute("clients",clients);
        model.addAttribute("agencesInactives",agencesInactif);
        Admin admin=adminRepository.findAll().get(0);
        model.addAttribute("admin",admin);
        return "Admin/index";

    }
   //Agences
    @GetMapping("/Admin/agences")
    public String afficherAgences(Model model) {
        List<Agence> agences = agenceRepository.findAll();
        model.addAttribute("agences", agences);
        Admin admin=adminRepository.findAll().get(0);
        model.addAttribute("admin",admin);
        return "Admin/Agence";

    }

    @GetMapping("/Admin/addagence")
    public String addAgences(Model model) {
        Admin admin=adminRepository.findAll().get(0);
        model.addAttribute("admin",admin);
        return "Admin/ajoutAgence";

    }

    @PostMapping("/Admin/Agence/Add")
    public String InsertAgences(Model model, Long id, String nom, String code, String horaire_debut, String horaire_fin,
                                String num_tele, String ville, String rue, String code_postal,String active) {
        Adresse adresse = new Adresse();
        adresse.setCode_postal(code_postal);
        adresse.setPays("Maroc");
        adresse.setRue(rue);
        adresse.setVille(ville);
        adresseRepository.save(adresse);
        Banque banque = banqueRepository.findById(1L).get();

        Agence agence = new Agence();
        if (id != null) {
            if(active.equals("active")){
                agence.setActive(true);
            }
            else if(active.equals("inactive")){
                agence.setActive(false);
            }
            agence.setId(id);
        }
        if(id==null){
            agence.setActive(true);
        }
        agence.setAdresse(adresse);
        agence.setCode(code);
        agence.setNom(nom);
        agence.setNum_tele(num_tele);
        agence.setHoraire_debut(horaire_debut);
        agence.setHoraire_fin(horaire_fin);
        agence.setBanque(banque);

        agenceRepository.save(agence);

        return "redirect:/Admin/agences";

    }

    @GetMapping("/Admin/modifieragence")
    public String update(Model model, Long id) {
        Agence agence=agenceRepository.findById(id).get();
        model.addAttribute("agence",agence);
        Admin admin=adminRepository.findAll().get(0);
        model.addAttribute("admin",admin);
        return "Admin/modifierAgence";
    }

    @GetMapping("/Admin/deleteagence")
    public String deleteagence(Model model, Long id) {

        Agence agence = agenceRepository.findById(id).get();
        agence.setActive(false);
        agenceRepository.save(agence);
         Agent agent = agence.getAgent();
        //adresseRepository.delete(agence.getAdresse());
       if (agent != null) {
           agent.setActive(false);
            agentRepository.save(agent);
        }
       Set<Client> clients = agence.getClients();
        if (clients != null) {
            for (Client client : clients) {
                client.setActive(false);
               clientRepository.save(client);
            }
        }


            return "redirect:/Admin/agences";

    }
    //Agents

    @GetMapping("/Admin/agents")
    public String afficherAgents(Model model) {
        List<Agent> agets = agentRepository.findAll();
      model.addAttribute("agents", agets);
        Admin admin=adminRepository.findAll().get(0);
        model.addAttribute("admin",admin);
        return "Admin/agents";

    }
    @GetMapping("/Admin/addAgent")
    public String addAgents(Model model) {
        List<Agence> agencess=agenceRepository.findAll();
        List<Agent> agents=agentRepository.findAll();
        List<Agence> agences=new ArrayList<>();
        for (Agence agence:agencess) {
                Agent agent=agentRepository.findByAgence(agence);
                //System.out.println(agent.getNom());
                 if(agent==null && agence.isActive()){
                     agences.add(agence);
                 }
        }
        model.addAttribute("agences",agences);
        Admin admin=adminRepository.findAll().get(0);
        model.addAttribute("admin",admin);
        return "Admin/ajoutAgents";

    }
    @PostMapping("/Admin/add/Agent")
    public String insereragents(Long id,String cin, String nom, String prenom, String email, String date_naissance,String code,
                                String num_tele,String agence,String active) throws ParseException {
        Agent agent=new Agent() ;
       // System.out.println("iddddd"+id);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        agent.setDate_naissance(df.parse(date_naissance));
        String password = generatePassword();
        if(id!=null){
            agent.setId(id);
            if(active.equals("active")){
                agent.setActive(true);
            }
            else{
                agent.setActive(false);
            }

        }
        else {
            agent.setActive(true);
        }

        agent.setCin(cin);
        agent.setNom(nom);
        agent.setPrenom(prenom);
        agent.setEmail(email);
        agent.setJoinDate(new Date());
        agent.setLastLoginDate(new Date());
        agent.setLastLoginDateDisplay(new Date());
        agent.setPassword(encodePassword(password));
        agent.setCode_agent(code);
        agent.setNotLocked(true);
        agent.setRoles(ROLE_AGENT.name());
        agent.setAuthorities(ROLE_AGENT.getAuthorities());
        agent.setNum_tele(num_tele);
        List<Agence> agen=agenceRepository.findByNom(agence);
        agent.setAgence(agen.get(0));
        String username=generateUsername();
        agent.setUsername(username);
       // emailService.sendNewPasswordEmail(nom, password, email);
        userRepository.save(agent);
        System.out.println("agent username: " + username + " password: " + password);
        return "redirect:/Admin/agents";

    }
    @GetMapping("/Admin/deleteagent")
    public String deleteagent(Model model, Long id) {
        Agent agent=agentRepository.findById(id).get();
        agent.setActive(false);
        agentRepository.save(agent);
        return "redirect:/Admin/agents";

    }
    @GetMapping("/Admin/updateagent")
    public String updateagent(Model model,Long id) {
        List<Agence> agencess=agenceRepository.findAll();
        List<Agent> agents=agentRepository.findAll();
        List<Agence> agences=new ArrayList<Agence>();
        for (Agence agence:agencess) {
            Agent agent=agentRepository.findByAgence(agence);
            //System.out.println(agent.getNom());
            if(agent==null && agence.isActive()){
                agences.add(agence);
            }
        }
        model.addAttribute("agences",agences);
        Agent agent=agentRepository.findById(id).get();
        model.addAttribute("agent",agent);
        Admin admin=adminRepository.findAll().get(0);
        model.addAttribute("admin",admin);
        return "Admin/edit_agent";
    }


    //clients
    @GetMapping("/Admin/clients")
    public String afficherclient(Model model){
        List<Client> clients=clientRepository.findAll();
        model.addAttribute("clients",clients);
        Admin admin=adminRepository.findAll().get(0);
        model.addAttribute("admin",admin);
        return "Admin/clients";
    }
    //Profile
    @GetMapping("/Admin/profile")
    public String afficherprofile(Model model){

        Admin admin=adminRepository.findAll().get(0);
        model.addAttribute("admin",admin);
        return "Admin/MonProfile";
    }


//encoder passwoord /generate username
    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    private String generatePassword() {
        return RandomStringUtils.randomAlphanumeric(8);
    }
    private String generateUsername() {
        return RandomStringUtils.randomAlphanumeric(6);
    }

    }



