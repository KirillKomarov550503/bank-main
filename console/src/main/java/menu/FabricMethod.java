package menu;


public class FabricMethod {
    private Menu menu;

    public Menu getMenu(Role role) {
        switch (role) {
            case VISITOR:
                menu = new VisitorMenu();
                break;
            case ADMIN:
                menu = new AdminMenu();
                break;
            case CLIENT:
                menu = new ClientMenu();
                break;
            default:
                System.out.println("Sorry. Unknown role");
                break;
        }
        return menu;
    }
}
