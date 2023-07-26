package softuni.exam.util;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Path;

public class XmlParserImpl implements XmlParser {
    @Override
    public <T> T fromFile(String filePath, Class<T> object) throws JAXBException, FileNotFoundException {
        final JAXBContext context = JAXBContext.newInstance(object);
        final Unmarshaller unmarshaller = context.createUnmarshaller();

        final FileReader fileReader = new FileReader(Path.of(filePath).toFile());

        return (T) unmarshaller.unmarshal(fileReader);
    }
}
