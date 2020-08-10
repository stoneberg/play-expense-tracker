package com.expense.tracker.play.permission.service;

import com.expense.tracker.play.permission.PermissionRes;
import com.expense.tracker.play.permission.PermissionRes.AdminDto;
import com.expense.tracker.play.permission.mapper.AdminMapper;
import com.expense.tracker.play.permission.mapper.ClientMapper;
import com.expense.tracker.play.permission.repository.AdminRepository;
import com.expense.tracker.play.permission.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.expense.tracker.play.permission.PermissionRes.*;

@RequiredArgsConstructor
@Service
public class PermissionService {

    private final AdminRepository adminRepository;
    private final ClientRepository clientRepository;
    private final AdminMapper adminMapper;
    private final ClientMapper clientmapper;

    public List<AdminDto> findAdmins() {
        return adminMapper.toAdminDtos(adminRepository.findAll());
    }

    public List<ClientDto> findClients() {
        return clientmapper.toClientDtos(clientRepository.findAll());
    }

}
