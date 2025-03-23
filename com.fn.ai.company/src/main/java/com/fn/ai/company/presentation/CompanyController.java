package com.fn.ai.company.presentation;

import com.fn.ai.common.application.CommonResponse;
import com.fn.ai.common.exception.code.CommonResponseCode;
import com.fn.ai.company.application.CompanyService;
import com.fn.ai.company.application.dto.request.CompanyCreateRequestDto;
import com.fn.ai.company.application.dto.request.CompanyUpdateRequestDto;
import com.fn.ai.company.application.dto.response.CompanyCreateResponseDto;
import com.fn.ai.company.application.dto.response.CompanyGetHubResponseDto;
import com.fn.ai.company.application.dto.response.CompanyResponseDto;
import com.fn.ai.company.application.dto.response.CompanyUpdateResponseDto;
import jakarta.ws.rs.PUT;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    @PostMapping("")
    public ResponseEntity<CommonResponse<CompanyCreateResponseDto>> createCompany(@RequestBody
    CompanyCreateRequestDto requestDto) {

        CompanyCreateResponseDto responseDto = companyService.createCompany(requestDto);
        return createResponse(responseDto, CommonResponseCode.CREATED);
    }

    private <T> ResponseEntity<CommonResponse<T>> createResponse(T responseDto,
        CommonResponseCode code) {
        return CommonResponse.of(code.getCode(), code.getMessage(), responseDto);
    }

    @PutMapping("/{company_id}")
    public ResponseEntity<CommonResponse<CompanyUpdateResponseDto>> updateCompany(
        @PathVariable UUID company_id, @RequestBody
    CompanyUpdateRequestDto requestDto) {

        CompanyUpdateResponseDto responseDto = companyService.updateCompany(company_id, requestDto);
        return createResponse(responseDto, CommonResponseCode.SUCCESS);
    }

    @DeleteMapping("{company_id}")
    public ResponseEntity<CommonResponse<CompanyResponseDto>> deleteCompany(
        @PathVariable UUID company_id) {

        CompanyResponseDto responseDto = companyService.deleteCompany(company_id);
        return createResponse(responseDto, CommonResponseCode.SUCCESS);
    }

    @GetMapping("/{company_id}")
    public ResponseEntity<CommonResponse<CompanyResponseDto>> getCompanyById(
        @PathVariable UUID company_id) {

        CompanyResponseDto responseDto = companyService.getCompanyById(company_id);
        return createResponse(responseDto, CommonResponseCode.SUCCESS);
    }

    @GetMapping("")
    public ResponseEntity<CommonResponse<Page<CompanyResponseDto>>> getAllCompany(
        @RequestParam int page,
        @RequestParam int size,
        @RequestParam String sortBy,
        @RequestParam boolean isAsc
    ) {

        Page<CompanyResponseDto> responseDto = companyService.getAllCompany(page, size, sortBy,
            isAsc);
        return createResponse(responseDto, CommonResponseCode.SUCCESS);
    }

    @GetMapping("/search")
    public ResponseEntity<CommonResponse<Page<CompanyResponseDto>>> searchCompany(
        @RequestParam int page,
        @RequestParam int size,
        @RequestParam String sortBy,
        @RequestParam boolean isAsc,
        @RequestParam String keyword
    ) {

        Page<CompanyResponseDto> responseDto = companyService.searchCompany(page, size, sortBy,
            isAsc,keyword);
        return createResponse(responseDto, CommonResponseCode.SUCCESS);
    }

    @GetMapping("/info")
    public CompanyGetHubResponseDto getHubIdOfCompany(
        @RequestParam UUID producerId,
        @RequestParam UUID receiverId
    ) {

      return companyService.getHubIdOfCompany(producerId,receiverId);
    }
}
