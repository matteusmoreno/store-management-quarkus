package br.com.matteusmoreno.service_order;

import br.com.matteusmoreno.accounts_receivable.AccountReceivableService;
import br.com.matteusmoreno.exception.exception_class.OutOfStockException;
import br.com.matteusmoreno.mapper.ServiceOrderMapper;
import br.com.matteusmoreno.service_order.constant.ServiceOrderStatus;
import br.com.matteusmoreno.service_order.service_order_request.CreateServiceOrderRequest;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.bson.types.ObjectId;

@ApplicationScoped
public class ServiceOrderService {

    private final ServiceOrderRepository serviceOrderRepository;
    private final ServiceOrderMapper serviceOrderMapper;
    private final AccountReceivableService accountReceivableService;

    @Inject
    public ServiceOrderService(ServiceOrderRepository serviceOrderRepository, ServiceOrderMapper serviceOrderMapper, AccountReceivableService accountReceivableService) {
        this.serviceOrderRepository = serviceOrderRepository;
        this.serviceOrderMapper = serviceOrderMapper;
        this.accountReceivableService = accountReceivableService;
    }

    public ServiceOrder createServiceOrder(CreateServiceOrderRequest request) {
        ServiceOrder serviceOrder = serviceOrderMapper.toEntity(request);
        serviceOrderRepository.persist(serviceOrder);

        return serviceOrder;
    }

    public ServiceOrder serviceOrderDetails(ObjectId id) {
        return serviceOrderRepository.findById(id);
    }

    // UPDATE SERVICE ORDER

    public ServiceOrder startServiceOrder(ObjectId id) {
        ServiceOrder serviceOrder = serviceOrderRepository.findById(id);

        serviceOrder.setServiceOrderStatus(ServiceOrderStatus.IN_PROGRESS);
        serviceOrderRepository.update(serviceOrder);
        accountReceivableService.createAccountReceivable(serviceOrder);

        return serviceOrder;
    }

    public ServiceOrder completeServiceOrder(ObjectId id) {
        ServiceOrder serviceOrder = serviceOrderRepository.findById(id);

        serviceOrder.setServiceOrderStatus(ServiceOrderStatus.COMPLETED);
        serviceOrderRepository.update(serviceOrder);
        accountReceivableService.payAccountReceivable(serviceOrder);

        return serviceOrder;
    }

    public ServiceOrder cancelServiceOrder(ObjectId id) {
        ServiceOrder serviceOrder = serviceOrderRepository.findById(id);

        serviceOrder.setServiceOrderStatus(ServiceOrderStatus.CANCELED);
        serviceOrderRepository.update(serviceOrder);
        accountReceivableService.cancelAccountReceivable(serviceOrder);

        return serviceOrder;
    }
}
