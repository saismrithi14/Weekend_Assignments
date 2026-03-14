package ServiceOrder.orders.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name="member-service", url="http://localhost:8081")
public interface MemberClient {
    @GetMapping("/oms/members/exists/{memberId}")
    public boolean containsMember(@PathVariable UUID memberId);
}
