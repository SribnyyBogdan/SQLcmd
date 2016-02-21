package integration;

import controller.Main;
import org.junit.Before;
import org.junit.Test;

import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

public class IntegrationTest {
    private ConfigurableInputStream in;
    private LogOutputStream out;

    @Before
    public void setup(){
        in = new ConfigurableInputStream();
        out = new LogOutputStream();
        System.setIn(in);
        System.setOut(new PrintStream(out));
    }

    @Test
    public void testCommandQuit(){
        //when
        in.addLine("quit");

        //given
        Main.main(new String[0]);

        //then
        assertEquals("It's a program SQLcmd!\r\n" +
                "Enter the command or help:\r\n", out.getData());
    }

    @Test
    public void testCommandConnect(){
        //when
        in.addLine("connect");
        in.addLine("test|postgres|1605");
        in.addLine("quit");


        //given
        Main.main(new String[0]);

        //then
        assertEquals("It's a program SQLcmd!\r\n" +
                "Enter the command or help:\r\n" +
                "Enter, please, database name, user name and password in the following format: database_name|user_name|password:\r\n" +
                "Connect is successful!\r\n" +
                "Enter the command or help:\r\n", out.getData());
    }

    @Test
    public void testCommandListWithConnect(){
        //when
        in.addLine("connect");
        in.addLine("test|postgres|1605");
        in.addLine("list");
        in.addLine("quit");


        //given
        Main.main(new String[0]);

        //then
        assertEquals("It's a program SQLcmd!\r\n" +
                "Enter the command or help:\r\n" +
                "Enter, please, database name, user name and password in the following format: database_name|user_name|password:\r\n" +
                "Connect is successful!\r\n" +
                "Enter the command or help:\r\n" +
                "[user]\r\n" +
                "Enter the command or help:\r\n", out.getData());
    }

    @Test
    public void testCommandListWithoutConnect(){
        //when
        in.addLine("list");
        in.addLine("quit");


        //given
        Main.main(new String[0]);

        //then
        assertEquals("It's a program SQLcmd!\r\n" +
                "Enter the command or help:\r\n" +
                "This command needs to connect to Database!\r\n" +
                "Enter the command or help:\r\n", out.getData());
    }

    @Test
    public void testDoesntExistCommand(){
        //when
        in.addLine("doesntexistcommand");
        in.addLine("quit");


        //given
        Main.main(new String[0]);

        //then
        assertEquals("It's a program SQLcmd!\r\n" +
                "Enter the command or help:\r\n" +
                "Does not exist command\r\n" +
                "Try again!\r\n" +
                "Enter the command or help:\r\n", out.getData());
    }

    @Test
    public void testCommandFindWithoutData(){
        //when
        in.addLine("connect");
        in.addLine("test|postgres|1605");
        in.addLine("clear");
        in.addLine("user");
        in.addLine("find");
        in.addLine("user");
        in.addLine("quit");


        //given
        Main.main(new String[0]);

        //then
        assertEquals("It's a program SQLcmd!\r\n" +
                "Enter the command or help:\r\n" +
                "Enter, please, database name, user name and password in the following format: database_name|user_name|password:\r\n" +
                "Connect is successful!\r\n" +
                "Enter the command or help:\r\n" +
                "Enter the table name\r\n" +
                "Enter the command or help:\r\n" +
                "Enter the table name\r\n" +
                "----------------------------\r\n" +
                "|name    |password|id      |\r\n" +
                "----------------------------\r\n" +
                "|        |        |        |\r\n" +
                "----------------------------\r\n" +
                "Enter the command or help:\r\n", out.getData());
    }

    @Test
    public void testCommandFindWithoutConnect(){
        //when
        in.addLine("find");
        in.addLine("user");
        in.addLine("quit");


        //given
        Main.main(new String[0]);

        //then
        assertEquals("It's a program SQLcmd!\r\n" +
                "Enter the command or help:\r\n" +
                "This command needs to connect to Database!\r\n" +
                "Enter the command or help:\r\n" +
                "Does not exist command\r\n" +
                "Try again!\r\n" +
                "Enter the command or help:\r\n", out.getData());
    }

    @Test
    public void testCommandHelp(){
        //when
        in.addLine("help");
        in.addLine("quit");

        //given
        Main.main(new String[0]);

        //then
        assertEquals("It's a program SQLcmd!\r\n" +
                "Enter the command or help:\r\n" +
                "Existing command:\r\n" +
                "\tconnect\r\n" +
                "\t\trequired parameters database_name|user_name|password\r\n" +
                "\t\t\tconnecting to Database\r\n" +
                "\thelp\r\n" +
                "\t\tthe output list of commands\r\n" +
                "\tlist\r\n" +
                "\t\tthe output of all tables on the screen\r\n" +
                "\tfind\r\n" +
                "\t\trequired parameter 'tableName'\r\n" +
                "\t\t\tthe output table data on the screen\r\n" +
                "\tinsert\r\n" +
                "\t\trequired parameter tableName|columnName1|columnValue1|...|...|columnNameN|columnValueN\r\n" +
                "\t\t\tinsert new row with data in table\r\n" +
                "\tclear\r\n" +
                "\t\trequired parameter 'tableName'\r\n" +
                "\t\t\tdelete all data from the table\r\n" +
                "\tquit\r\n" +
                "\t\texiting from the program\r\n" +
                "Enter the command or help:\r\n", out.getData());
    }

    @Test
    public void testCommandConnectWithTwoParameter(){
        //when
        in.addLine("connect");
        in.addLine("test|postgres");
        in.addLine("quit");


        //given
        Main.main(new String[0]);

        //then
        assertEquals("It's a program SQLcmd!\r\n" +
                "Enter the command or help:\r\n" +
                "Enter, please, database name, user name and password in the following format: database_name|user_name|password:\r\n" +
                "Cause of failure:Invalid number of parameters, must be 3, but entered 2\r\n" +
                "Try again!\r\n" +
                "Connect is successful!\r\n" +
                "Enter the command or help:\r\n", out.getData());
    }

    @Test
    public void testCommandClear(){
        //when
        in.addLine("connect");
        in.addLine("test|postgres|1605");
        in.addLine("clear");
        in.addLine("user");
        in.addLine("quit");

        //given
        Main.main(new String[0]);

        //then
        assertEquals("It's a program SQLcmd!\r\n" +
                "Enter the command or help:\r\n" +
                "Enter, please, database name, user name and password in the following format: database_name|user_name|password:\r\n" +
                "Connect is successful!\r\n" +
                "Enter the command or help:\r\n" +
                "Enter the table name\r\n" +
                "Enter the command or help:\r\n", out.getData());
    }

    @Test
    public void testCommandFind(){
        //when
        in.addLine("connect");
        in.addLine("test|postgres|1605");
        in.addLine("clear");
        in.addLine("user");
        in.addLine("insert");
        in.addLine("user|id|10|name|Stiven|password|pass");
        in.addLine("find");
        in.addLine("user");
        in.addLine("quit");


        //given
        Main.main(new String[0]);

        //then
        assertEquals("It's a program SQLcmd!\r\n" +
                "Enter the command or help:\r\n" +
                "Enter, please, database name, user name and password in the following format: database_name|user_name|password:\r\n" +
                "Connect is successful!\r\n" +
                "Enter the command or help:\r\n" +
                "Enter the table name\r\n" +
                "Enter the command or help:\r\n" +
                "Enter, please, table name and data for inserting in the following format: table_name|columnName1|columnValue1|...|...|columnNameN|columnValueN:\r\n" +
                "Enter the command or help:\r\n" +
                "Enter the table name\r\n" +
                "----------------------------\r\n" +
                "|name    |password|id      |\r\n" +
                "----------------------------\r\n" +
                "|Stiven  |pass    |10      |\r\n" +
                "----------------------------\r\n" +
                "Enter the command or help:\r\n", out.getData());
    }

}
