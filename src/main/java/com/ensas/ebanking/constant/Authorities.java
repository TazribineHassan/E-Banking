package com.ensas.ebanking.constant;

public class Authorities {
    public static final  String[] CLIENT_AUTHORITIES = {"user:read", "manage_account",
                                                        "transfer"};
    public static final  String[] AGENT_AUTHORITIES = {"user:read", "user:update",
                                                       "manage_account", "manage_clients"};
    public static final  String[] ADMIN_AUTHORITIES = {"user:read", "user:update","manage_clients",
                                                       "manage_account", "manage_agencies",
                                                       "manage_agents", "manage_clients_statuses",
                                                       "manage_app"};
}
