import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;
import java.io.FileInputStream;
import java.io.InputStream;

public class XmlParser implements AddressParser {

    //формат: <item city="Барнаул" street="Дальняя улица" house="56" floor="2" />
    //используем StAX - он читает файл по частям, а DOM загрузил бы все 200+ мб в память
    @Override
    public void parse(String filePath, Statistics statistics) throws Exception {
        XMLInputFactory factory = XMLInputFactory.newInstance();

        try (InputStream in = new FileInputStream(filePath)) {
            XMLStreamReader reader = factory.createXMLStreamReader(in);

            while (reader.hasNext()) {
                int event = reader.next();
                if (event == XMLStreamConstants.START_ELEMENT && reader.getLocalName().equals("item")) {
                    String city = reader.getAttributeValue(null, "city");
                    String street = reader.getAttributeValue(null, "street");
                    int house = Integer.parseInt(reader.getAttributeValue(null, "house"));
                    int floor = Integer.parseInt(reader.getAttributeValue(null, "floor"));
                    statistics.add(new Address(city, street, house, floor));
                }
            }
            reader.close();
        }
    }
}
