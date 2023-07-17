import TableDTO.HtmlDTO;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

import java.io.InputStream;

public class TableMain {
    public static void main(String[] args) throws JAXBException {
        JAXBContext jaxbListContext = JAXBContext.newInstance(HtmlDTO.class);
        Unmarshaller listUnmarshaller = jaxbListContext.createUnmarshaller();

        InputStream resourceAsStream = UnmarshallerMain.class.getResourceAsStream("table.xml");
        HtmlDTO fromFile = (HtmlDTO) listUnmarshaller.unmarshal(resourceAsStream);
        System.out.println();

    }
}
