package com.expense.tracker.play.permission.mapper;

import com.expense.tracker.play.config.MapStructMapperConfig;
import com.expense.tracker.play.permission.PermissionRes;
import com.expense.tracker.play.permission.PermissionRes.AdminDto;
import com.expense.tracker.play.permission.domian.Admin;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = MapStructMapperConfig.class)
public interface AdminMapper {
    AdminDto toAdminDto(Admin admin);
    List<AdminDto> toAdminDtos(List<Admin> admins);
}
