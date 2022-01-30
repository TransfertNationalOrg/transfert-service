package ma.ensa.agent;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name="agent-service")
public interface AgentFeign {
    //Get all agents from agent-service
    @GetMapping("agent/")
    List<AgentDTO> findAll();

    //Get an agent by id from agent-service
    @GetMapping("agent/{id}")
    AgentDTO getAgentById(@PathVariable("id") Long id);

    //Update an agent
    @PutMapping("agent/")
    AgentDTO update(@RequestBody AgentDTO agentDTO);
}
