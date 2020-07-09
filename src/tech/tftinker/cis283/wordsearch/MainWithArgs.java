package tech.tftinker.cis283.wordsearch;

public class MainWithArgs {
    public static void run(String[] args){
        for (int i = 0; i < args.length; i++) {
            if (args[i].equalsIgnoreCase("Help")) {
                helpScreen(args, i);
            } else if (args[i].equalsIgnoreCase("Open")) {
                Main.debugAutoOpen = true;
                System.out.println(ConsoleColors.BLUE + "Set Auto Open PDF to TRUE" + ConsoleColors.RESET);
            } else if (args[i].equalsIgnoreCase("All")) {
                Main.debugShowEverythingFlag = true;
                System.out.println(ConsoleColors.BLUE + "Set Show Everything to TRUE" + ConsoleColors.RESET);
            } else if (args[i].equalsIgnoreCase("Word")) {
                i++;
                if (i < args.length){
                    Main.wordsFilePath = args[i];
                    System.out.println(ConsoleColors.BLUE + "Set new words path" + ConsoleColors.RESET);
                } else {
                    System.out.println(ConsoleColors.RED_BOLD +
                            "ERROR: Missing Argument" +
                            ConsoleColors.RESET);
                    helpScreen(args, i);
                }
            } else if (args[i].equalsIgnoreCase("Out")) {
                i++;
                if (i < args.length){
                    Main.pdfFilePath = args[i];
                    System.out.println(ConsoleColors.BLUE + "Set new PDF output path" + ConsoleColors.RESET);
                } else {
                    System.out.println(ConsoleColors.RED_BOLD +
                            "ERROR: Missing Argument" +
                            ConsoleColors.RESET);
                    helpScreen(args, i);
                }
            } else {
                System.out.println(ConsoleColors.RED_BOLD +
                        "ERROR: Argument (" + ConsoleColors.RESET +
                        args[i] + ConsoleColors.RED_BOLD + ") does not have a mach" +
                        ConsoleColors.RESET);
                helpScreen(args, i);
            }
        }
    }

    private static void helpScreen(String[] args, int i){
        System.out.println(ConsoleColors.BLUE);

        System.out.println("--------------------------------");
        System.out.println("| Welcome to Word Search Maker |");
        System.out.println("--------------------------------------");
        System.out.println("| Use \"Help\" to see this screen      |");
        System.out.println("| Use \"Open\" to auto open pdf puzzle |");
        System.out.println("| Use \"All\" to show everything       |");
        System.out.println("| Use \"Word\" to set word file path   |--");
        System.out.println("| Use \"Out\" to set the PDF file output |");
        System.out.println("----------------------------------------");

        // Examples
        i++;
        if (i < args.length){
            if (args[i].equalsIgnoreCase("Word")){
                System.out.println("| Example for \"Word\"                    |");
                System.out.println("|Open \"C:\\Users\\tztz8\\Desktop\\words.txt\"|");
                System.out.println("----------------------------------------");
            } else if (args[i].equalsIgnoreCase("Out")){
                System.out.println("| Example for \"Out\"                     |");
                System.out.println("|Out \"C:\\Users\\tztz8\\Desktop\\puzzle.pdf\"|");
                System.out.println("----------------------------------------");
            } else {
                System.out.println("| No Example for \"" + args[i] + "\" |");
                System.out.println("-------------------------");
            }
        }

        System.out.println(ConsoleColors.RESET);
        System.exit(0);
    }
}
