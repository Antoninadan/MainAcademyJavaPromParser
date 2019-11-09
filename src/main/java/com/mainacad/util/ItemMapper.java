package com.mainacad.util;

import com.mainacad.model.Item;
import com.mainacad.model.Items;

import java.io.StringWriter;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

public class ItemMapper {
    public static String mapToJson(List<Item> items) {
        return null;
    }

    public static String mapToXml(List<Item> items) {
        String result = null;
        Items itemList = new Items(items);
        try {
            JAXBContext contextObj = JAXBContext.newInstance(Items.class);
            Marshaller marshallerObj = contextObj.createMarshaller();
            marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            StringWriter stringWriter = new StringWriter();
            marshallerObj.marshal(itemList, stringWriter);
            return stringWriter.toString();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return result;
    }

}