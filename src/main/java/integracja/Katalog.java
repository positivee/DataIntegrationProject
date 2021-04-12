package integracja;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.sql.SQLException;
import java.util.List;

@WebService()
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT)
public class Katalog {

    @WebMethod
    public Integer getCountOfManufacturer(String manufacturer) {
        DatabaseHelper databaseHelper = new DatabaseHelper();
        return databaseHelper.getCountOfManufacturer(manufacturer);
    }

    @WebMethod
    public Integer getCountOfResolution(String resolution) {
        DatabaseHelper databaseHelper = new DatabaseHelper();
        return databaseHelper.getCountOfResolution(resolution);
    }

    @WebMethod
    public List<String> distinctList(String makeDistinct) {
        DatabaseHelper databaseHelper = new DatabaseHelper();
        try {
            return databaseHelper.distinctList(makeDistinct);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @WebMethod
    public List<String[]> getListOfelementValue(String columnName, String value) {
        DatabaseHelper databaseHelper = new DatabaseHelper();
        try {
            return databaseHelper.getListOfelementValue(columnName, value);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

}