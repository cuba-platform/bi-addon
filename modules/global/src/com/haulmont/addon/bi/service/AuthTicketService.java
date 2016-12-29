package com.haulmont.addon.bi.service;


public interface AuthTicketService {
    String NAME = "cubabi_AuthTicketService";

    String generateTicket();

    boolean isTicketGenerated(String ticket, String username);
}