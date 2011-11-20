package org.springframework.social.linkedin.api.impl.json;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.social.linkedin.api.CodeAndName;
import org.springframework.social.linkedin.api.Product.ProductRecommendation;
import org.springframework.social.linkedin.api.impl.json.CompanyMixin.StringListDeserializer;
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductMixin {
	@JsonCreator
	public ProductMixin(
			@JsonProperty(value="creationTimestamp") Date creationTimestamp, 
			@JsonProperty(value="description") String description,
			@JsonProperty(value="features") @JsonDeserialize(using=StringListDeserializer.class) List<String> features, 
			@JsonProperty(value="id") int id, 
			@JsonProperty(value="logoUrl") String logoUrl, 
			@JsonProperty(value="name") String name,
			@JsonProperty(value="numRecommendations") int numRecommendations, 
			@JsonProperty(value="productCategory") CodeAndName productCategory,
			@JsonProperty(value="recommendations") @JsonDeserialize(using=ProductRecommendationListDeserializer.class)  List<ProductRecommendation> recommendations,
			@JsonProperty(value="type") CodeAndName type, 
			@JsonProperty(value="websiteUrl") String websiteUrl) {}
	
	public static final class ProductRecommendationListDeserializer extends JsonDeserializer<List<ProductRecommendation>>  {
		public List<ProductRecommendation> deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
			ObjectMapper mapper = new ObjectMapper();
			mapper.setDeserializationConfig(ctxt.getConfig());
			jp.setCodec(mapper);
			if(jp.hasCurrentToken()) {
				JsonNode dataNode = jp.readValueAsTree().get("values");
				if (dataNode != null) {
					return mapper.readValue(dataNode, new TypeReference<List<ProductRecommendation>>() {} );
				}
			}
			return null;
		}
	}
}
