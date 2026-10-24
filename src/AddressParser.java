//общий контракт для парсеров csv и xml
//записи не складываются в список, а сразу передаются в статистику - так быстрее и меньше памяти
public interface AddressParser {

    void parse(String filePath, Statistics statistics) throws Exception;
}
