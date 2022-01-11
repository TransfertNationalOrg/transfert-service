package ma.ensa.agent;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name="transfert-service")
public interface AgentFeign {
    //Get all agents from agent-service
    @GetMapping("agent/")
    List<AgentDTO> findAll();

    //Get a agent by id from agent-service
    @GetMapping("agent/{idAgent}")
    AgentDTO getAgentById(@PathVariable("id") Long id);
}
