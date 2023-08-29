import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Login {
    private static final int MAX_LOGIN_ATTEMPTS = 5;

    private static Map<String, String> userDatabase = new HashMap<>();
    private static Map<String, Integer> loginAttempts = new HashMap<>();
    Scanner scanner=new Scanner(System.in);
    public Login(){

    }
    public void UserLogin(){
        loadUserData();

        Scanner scanner = new Scanner(System.in);

        System.out.println("请输入用户名：");
        String username = scanner.nextLine();

        if (isAccountLocked(username)) {
            System.out.println("账户已锁定，请联系管理员。");
            return;
        }

        System.out.println("请输入密码：");
        String password = scanner.nextLine();

        if (UservalidateLogin(username, password)) {
            System.out.println("登录成功！");
            // 如果登录成功，重置登录尝试次数
            resetLoginAttempts(username);
            EnterUser();
        } else {
            int attempts = incrementLoginAttempts(username);
            System.out.println("登录失败，还剩 " + (MAX_LOGIN_ATTEMPTS - attempts) + " 次尝试机会。");
            if (attempts >= MAX_LOGIN_ATTEMPTS) {
                System.out.println("已连续输入错误密码 " + MAX_LOGIN_ATTEMPTS + " 次，账户已锁定。");
            }
        }

    }
    public static boolean UservalidateLogin(String username, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader("user_data.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String storedUsername = parts[0];
                String storedPassword = parts[1];
                if (username.equals(storedUsername) && password.equals(storedPassword)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public  void loadUserData() {
        try (BufferedReader reader = new BufferedReader(new FileReader("user_data.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                userDatabase.put(parts[0], parts[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int incrementLoginAttempts(String username) {
        int attempts = loginAttempts.getOrDefault(username, 0) + 1;
        loginAttempts.put(username, attempts);
        return attempts;
    }

    public static void resetLoginAttempts(String username) {
        loginAttempts.remove(username);
    }
    public static boolean isAccountLocked(String username) {
        return loginAttempts.getOrDefault(username, 0) >= MAX_LOGIN_ATTEMPTS;
    }
    public void EnterUser(){
        System.out.println("登录成功！！");
        System.out.println("欢迎尊敬的用户");
        boolean flag=true;
        while(flag) {
            System.out.println("*******************");
            System.out.println("      用户界面");
            System.out.println("*******************");
            System.out.println("1.密码管理         2.购物车       0.退出登录");
            System.out.println("请输入数字（1,2,0）");
            int a = scanner.nextInt();
            Password password = new Password();
            ShoppingCar shoppingCar = new ShoppingCar();
            while (true) {
                if (a == 1) {
                    password.UserPasswordSystem();
                } else if (a == 2) {
                    shoppingCar.ShopSystem();
                } else if (a == 0) {
                    flag=false;
                    System.out.println("用户已退出登录！！");
                    break;
                }
                if (a == 1 || a == 2) {
                    break;
                } else {
                    System.out.println("输入错误，请重新输入");
                    a = scanner.nextInt();
                }
            }
        }
    }



/*****************************************/

    public void MasterLogin() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("请输入用户名:");
            String username = scanner.nextLine();
            System.out.println("请输入密码:");
            String password = scanner.nextLine();
            if (validateLogin(username, password)) {
                System.out.println("登录成功");
                EnterMaster();
                break;
            } else {
                System.out.println("登录失败，请重新输入");
            }
        }

    }
    public static boolean validateLogin(String username, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader("D:\\IDEAbianma\\Shop\\src\\master_data.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String storedUsername = parts[0];
                String storedPassword = parts[1];
                if (username.equals(storedUsername) && password.equals(storedPassword)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void EnterMaster(){
        System.out.println("登陆成功！！");
        System.out.println("欢迎尊敬的管理员");
        boolean flag=true;
        while (flag) {
            System.out.println("*******************");
            System.out.println("      管理员界面");
            System.out.println("*******************");
            System.out.println("1.密码管理         2.客户管理         3.商品管理       0.退出登录");
            System.out.println("请输入数字（1,2,3,0）");
            int a;
            Password Masterpassword = new Password();
            Customer customer = new Customer();
            Goods goods = new Goods();
            while (true) {
                try {
                    a = scanner.nextInt();
                    break;
                } catch (Exception e) {
                    System.out.println("输入错误！！");
                }
            }
            switch (a) {
                case 1:
                    Masterpassword.MasterPasswordSystem();
                    break;
                case 2:
                    customer.CustomerSystem();
                    break;
                case 3:
                    goods.GoodsSystem();
                    break;
                case 0:
                    flag=false;
                    System.out.println("管理员已退出登录！！");
                    break;
            }
        }
    }
}
