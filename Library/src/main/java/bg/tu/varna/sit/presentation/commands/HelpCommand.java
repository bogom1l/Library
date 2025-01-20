package bg.tu.varna.sit.presentation.commands;

import bg.tu.varna.sit.presentation.Command;

public class HelpCommand implements Command {

    public HelpCommand() {
    }

    @Override
    public void execute() {
        System.out.println("Available commands:");
        System.out.println("1. open <file.xml>");
        System.out.println("2. close");
        System.out.println("3. save");
        System.out.println("4. saveas <file.xml>");
        System.out.println("5. help");
        System.out.println("6. exit");
        System.out.println("7. login");
        System.out.println("8. logout");
        System.out.println("9. books all");
        System.out.println("10. books add <author> <title> <genre> <description> <year> <keywords> <rating> <isbn>");
        System.out.println("11. books remove <isbn>");
        System.out.println("12. books info <isbn_value>");
        System.out.println("13. books find <option> <option_string>");
        System.out.println("14. books sort <option> [asc | desc]");
        System.out.println("15. users add <user> <password>");
        System.out.println("16. users remove <user>");
    }
}