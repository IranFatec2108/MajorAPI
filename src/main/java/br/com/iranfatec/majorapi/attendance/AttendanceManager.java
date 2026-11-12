package br.com.iranfatec.majorapi.attendance;


import br.com.iranfatec.majorapi.service.ProvidedService;
import br.com.iranfatec.majorapi.service.ProvidedServiceRepository;
import br.com.iranfatec.majorapi.service.ServiceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class AttendanceManager {

    private final AttendanceRepository attendanceRepository;
    private final ProvidedServiceRepository providedServiceRepository;

    public AttendanceManager(AttendanceRepository attendanceRepository, ProvidedServiceRepository providedServiceRepository) {
        this.attendanceRepository = attendanceRepository;
        this.providedServiceRepository = providedServiceRepository;
    }

    public AttendanceResponseDto registerAttendance(AttendanceCreateRequestDto dto) {
        ProvidedService service = providedServiceRepository.findByIdAndActiveTrue(dto.providedServiceId())
                .orElseThrow(() -> new ServiceNotFoundException("Serviço não encontrado,ou inativo. Por favor, anexe um serviço válido"));

        Attendance attendance = toEntity(dto, service);
        Attendance attendanceSaved = attendanceRepository.save(attendance);
        return toResponseDto(attendanceSaved);

    }

    public List<AttendanceResponseDto> listAllValidAttendances() {
        List<Attendance> entities = attendanceRepository.findAllValidOrderByDateTimeDesc();

        return entities.stream()
                .map(this::toResponseDto)
                .collect(toList());
    }

    public List<AttendanceResponseDto> listAllInvalidAttendances() {
        List<Attendance> entities = attendanceRepository.findAllInvalidOrderByDateTimeDesc();

        return entities.stream()
                .map(this::toResponseDto)
                .collect(toList());
    }

    public AttendanceResponseDto findAttendanceById(Long id) {

        return attendanceRepository.findById(id)
                .map(this::toResponseDto)
                .orElseThrow(() -> new AttendanceNotFoundException("Atendimento não existente. Por favor, digitar um ID válido"));
    }

    @Transactional
    public AttendanceResponseDto updateAttendance(AttendanceUpdateRequestDto dto, Long id) {

        Attendance attendance = attendanceRepository.findById(id)
                .orElseThrow(() -> new AttendanceNotFoundException("Atendimento não encontrado, digite um atendimento válido"));

        if (dto.providedServiceId() != null) {
            ProvidedService service = providedServiceRepository
                    .findByIdAndActiveTrue(dto.providedServiceId())
                    .orElseThrow(() -> new ServiceNotFoundException("Serviço não encontrado ou inativo (ID: " + dto.providedServiceId() + ")"));

            attendance.setProvidedService(service);
            attendance.setServiceNameSnapshot(service.getName());
        }
        if (dto.amountPaid() != null) {
            attendance.setAmountPaid(dto.amountPaid());
        }
        Attendance attendanceSaved = attendanceRepository.save(attendance);

        return toResponseDto(attendanceSaved);
    }

    public AttendanceResponseDto deactivateAttendance(Long id) {
        Attendance attendance = attendanceRepository.findById(id)
                .orElseThrow(() -> new AttendanceNotFoundException(
                        "Atendimento não encontrado. Informe um ID válido"));

        if (!attendance.getValid()) {
            return toResponseDto(attendance);
        }

        attendance.setValid(false);
        Attendance attendanceSaved = attendanceRepository.save(attendance);
        return toResponseDto(attendanceSaved);
    }


    public AttendanceResponseDto reactivateAttendance(Long id) {
        Attendance attendance = attendanceRepository.findById(id)
                .orElseThrow(() -> new AttendanceNotFoundException(
                        "Atendimento não encontrado. Informe um ID válido"));

        if (attendance.getValid()) {
            return toResponseDto(attendance);
        }

        attendance.setValid(true);
        Attendance saved = attendanceRepository.save(attendance);
        return toResponseDto(saved);
    }


    private AttendanceResponseDto toResponseDto(Attendance attendance) {
        return new AttendanceResponseDto(
                attendance.getId(),
                attendance.getServiceNameSnapshot(),
                attendance.getAmountPaid(),
                attendance.getDateTime(),
                attendance.getValid()
        );
    }

    private Attendance toEntity(AttendanceCreateRequestDto request, ProvidedService service) {
        Attendance attendance = new Attendance();
        attendance.setProvidedService(service);
        attendance.setServiceNameSnapshot(service.getName());
        attendance.setAmountPaid(request.amountPaid());
        attendance.setDateTime(LocalDateTime.now());

        return attendance;
    }

}
