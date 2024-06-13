package br.com.matteusmoreno.service_order.service_order_response;

import br.com.matteusmoreno.customer.customer_response.ResumeCustomerResponse;
import br.com.matteusmoreno.employee.employee_response.ResumeEmployeeResponse;
import br.com.matteusmoreno.service_order.ServiceOrder;
import br.com.matteusmoreno.service_order_product.service_order_product_response.ResumeServiceOrderProductResponse;
import org.bson.types.ObjectId;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public record ServiceOrderDetailsResponse(
        ObjectId serviceOrderId,
        ResumeCustomerResponse customer,
        ResumeEmployeeResponse employee,
        List<ResumeServiceOrderProductResponse> products,
        BigDecimal laborPrice,
        BigDecimal totalCost,
        String serviceOrderStatus) {

    public ServiceOrderDetailsResponse(ServiceOrder serviceOrder) {
        this(
                serviceOrder.getId(),
                new ResumeCustomerResponse(serviceOrder.getCustomer()),
                new ResumeEmployeeResponse(serviceOrder.getEmployee()),
                serviceOrder.getServiceOrderProducts().stream()
                        .map(ResumeServiceOrderProductResponse::new)
                        .collect(Collectors.toList()),
                serviceOrder.getLaborPrice(),
                serviceOrder.getTotalCost(),
                serviceOrder.getServiceOrderStatus().getDisplayName()
        );
    }
}
