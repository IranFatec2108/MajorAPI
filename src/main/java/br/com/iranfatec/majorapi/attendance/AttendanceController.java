package br.com.iranfatec.majorapi.attendance;


import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/attendance")
public class AttendanceController {

    private final AttendanceManager attendanceManager;

    public AttendanceController(AttendanceManager attendanceManager) {
        this.attendanceManager = attendanceManager;
    }

    @PostMapping
    public ResponseEntity<AttendanceResponseDto> registerAttendance(@Valid @RequestBody AttendanceCreateRequestDto dto){
        AttendanceResponseDto responseRegisterAttendance = attendanceManager.registerAttendance(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseRegisterAttendance);
    }

    @GetMapping("/active")
    public ResponseEntity<List<AttendanceResponseDto>> listAllValidAttendance(){
        List<AttendanceResponseDto>  responseAttendanceValidList = attendanceManager.listAllValidAttendances();
        return ResponseEntity.ok(responseAttendanceValidList);
    }

    @GetMapping("/inactive")
    public ResponseEntity<List<AttendanceResponseDto>> listAllInvalidAttendance(){
        List<AttendanceResponseDto>  responseAttendanceInvalidList = attendanceManager.listAllInvalidAttendances();
        return ResponseEntity.ok(responseAttendanceInvalidList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AttendanceResponseDto> findAttendanceById(@PathVariable Long id){
        AttendanceResponseDto responseAttendanceById = attendanceManager.findAttendanceById(id);
        return ResponseEntity.ok().body(responseAttendanceById);
    }




}
