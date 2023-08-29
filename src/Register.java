import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Register {
    Scanner scanner=new Scanner(System.in);
    public Register(){

    }

    public void UserRegister() {

        System.out.println("欢迎注册！请输入用户名（用户名长度不少于5个字符）：");
        String username = scanner.nextLine();
        while (true) {
            if (validateUsername(username)) {
                System.out.println("请输入密码（密码长度大于8个字符，必须是大小写字母、数字和标点符号的组合）：");
                String password = scanner.nextLine();
                while(true) {
                    if (validatePassword(password)) {
                        // 将用户名和密码存储在文件中
                        if (storeUserInformation(username, password)) {
                            System.out.println("注册成功！");
                            break;
                        } else {
                            System.out.println("注册失败，请重试。");
                        }
                    } else {
                        System.out.println("密码不符合要求！");
                        System.out.println("请重新输入密码（密码长度大于8个字符，必须是大小写字母、数字和标点符号的组合）");
                        password = scanner.nextLine();
                    }
                }
                break;
            } else {
                System.out.println("用户名不符合要求！");
                System.out.println("请重新输入（用户名长度不少于5个字符）");
                username = scanner.nextLine();
            }
        }
    }
    public static boolean validateUsername(String username) {
        return username.length() >= 5;
    }

    public static boolean validatePassword(String password) {
        // 密码长度大于8，包含大小写字母、数字和标点符号
        String passwordPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        return Pattern.matches(passwordPattern, password);
    }

    public static boolean storeUserInformation(String username, String password) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("user_data.txt", true))) {
            // 在文件末尾追加用户信息
            writer.write(username + "," + password + "\n");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
