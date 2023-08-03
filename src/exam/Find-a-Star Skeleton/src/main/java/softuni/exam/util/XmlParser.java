package softuni.exam.util;

import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Path;

@Component
public class XmlParser {
	
    public <T> T fromFile (String filePath, Class<T> object) throws JAXBException, FileNotFoundException, JAXBException, FileNotFoundException {
        final JAXBContext context = JAXBContext.newInstance(object);
        final Unmarshaller unmarshaller = context.createUnmarshaller();

        File file = Path.of(filePath).toFile();
        
        final FileReader fileReader = new FileReader(file);

        return (T) unmarshaller.unmarshal(fileReader);
    }

}
