package com.expense.tracker.play.permission.mapper;

import com.expense.tracker.play.config.MapStructMapperConfig;
import com.expense.tracker.play.permission.PermissionRes.ClientDto;
import com.expense.tracker.play.permission.domian.Client;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = MapStructMapperConfig.class)
public interface ClientMapper {
    ClientDto toClientDto(Client client);
    List<ClientDto> toClientDtos(List<Client> clients);
}
