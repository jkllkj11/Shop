import java.util.Scanner;

public class Main {
    public static int System(){
        System.out.println("******************************");
        System.out.println("          购物管理系统");
        System.out.println("******************************");
        System.out.println("1.注册            2.登录            0.退出");
        System.out.println("请输入数字（0、1、2）");
        Scanner scanner=new Scanner(System.in);
        int a=scanner.nextInt();
        return a;
    }
    public static void main(String[] args) {
        while(true) {
            int a=System();
            if (a == 1) {
                while (true) {
                    System.out.println("用户注册界面");
                    Register register = new Register();
                    register.UserRegister();
                    break;
                }
            } else if (a == 2) {
                System.out.println("******************************");
                System.out.println("           请选择登陆方式");
                System.out.println("******************************");
                System.out.println("1.用户登录             2.管理员登陆");
                System.out.println("请输入数字（1、2）");
                Scanner scanner = new Scanner(System.in);
                a = scanner.nextInt();
                Login login = new Login();
                while (true) {
                    if (a == 1) {
                        login.UserLogin();
                    } else if (a == 2) {
                        login.MasterLogin();
                    }
                    if (a == 1 || a == 2) {
                        break;
                    } else {
                        System.out.println("输入错误，请重新输入");
                    }
                }
            } else if (a == 0) {
                System.exit(0);
            }

        }
    }
}