package mk.rabbitmq;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class WorkUnit {

    private final String id;
    private final String definition;
    private final String spanTraceId;
    private final String parentId;
    
	public String getParentId() {
		return parentId;
	}

	@JsonCreator
    public WorkUnit(
    				@JsonProperty("parentId") String parentId,
                    @JsonProperty("spanTraceId") String spanTraceId,
                    @JsonProperty("id") String id,
                    @JsonProperty("definition") String definition
                    ) {

        this.parentId = parentId;
        this.spanTraceId = spanTraceId;
        this.id = id;
        this.definition = definition;
    }

    public String getId() {
        return id;
    }


    public String getDefinition() {
        return definition;
    }

    public String getSpanTraceId() {
		return spanTraceId;
	}

	
    
}