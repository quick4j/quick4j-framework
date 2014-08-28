package com.github.quick4j.plugin.datagrid;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.quick4j.plugin.datagrid.meta.Column;
import com.github.quick4j.plugin.datagrid.meta.Header;
import com.github.quick4j.plugin.datagrid.meta.Toolbar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author zhaojh
 */
public class DataGridDeserializer extends JsonDeserializer {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private ObjectMapper mapper;

    public DataGridDeserializer(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public Object deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException, JsonProcessingException {
        JsonNode root = jsonParser.getCodec().readTree(jsonParser);
        String name = root.get("name").asText();
        String dataModel = root.get("dataModel").asText();
        JsonNode columnsNode = root.get("columns");
        JsonNode toolbarNode = root.get("toolbar");

        logger.debug("name: {}", name);
        logger.debug("dataModel: {}", dataModel);

        List<Header> columns = deserializeColumns(columnsNode);
        Toolbar toolbar = deserializeToolbar(toolbarNode);

        return new DataGrid(name, dataModel, columns, toolbar);
    }

    private List<Header> deserializeColumns(JsonNode columnsNode) throws IOException {
        if(null != columnsNode){
            List<Header> columns = new ArrayList<Header>();
            for(Iterator<JsonNode> iterator = columnsNode.iterator(); iterator.hasNext();){
                JsonNode node = iterator.next();
                Header header = new Header();
                for(Iterator<JsonNode> it = node.iterator(); it.hasNext();){
                    String colJson = it.next().toString();

                    logger.debug("column:{}", colJson);

                    Column column = mapper.readValue(colJson, Column.class);
                    header.add(column);
                }
                columns.add(header);
            }
            return columns;
        }else{
            return null;
        }
    }

    private Toolbar deserializeToolbar(JsonNode toolbarNode) throws IOException {
        if(null != toolbarNode){
            String toolbarJson = toolbarNode.toString();

            logger.debug("toolbar: {}", toolbarJson);

            return mapper.readValue(toolbarJson, Toolbar.class);
        }else{
            logger.debug("no toolbar.");
            return null;
        }
    }
}
