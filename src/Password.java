import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Password {
    private static Map<String, String> userDatabase = new HashMap<>();

    Scanner scanner=new Scanner(System.in);
    public  Password(){

    }
    public void UserPasswordSystem(){
        boolean flag=true;
        while(flag) {
            System.out.println("****************************");
            System.out.println("   欢迎来到客户密码管理系统");
            System.out.println("   1.修改自身密码");
            System.out.println("   2.重置自身密码");
            System.out.println("   0.退出客户密码管理系统");
            System.out.println("****************************");
            System.out.println("请输入数字（1，2，0）");
            int a;
            while (true) {
                try {
                    a = scanner.nextInt();
                    break;
                } catch (Exception e) {
                    System.out.println("输入错误，请重新输入");
                }
            }
            switch (a) {
                case 1:
                    XiGaiUserPassword();
                    break;
                case 2:
                    ChongZhiUserPassword();
                    break;
                case 0:
                    flag=false;
                    System.out.println("退出客户密码管理系统成功！！");
                    break;
            }
        }
    }
    public void XiGaiUserPassword(){
        loadUserData();
        System.out.println("请输入用户名：");
        scanner.nextLine();
        String username = scanner.nextLine();
        if (!userDatabase.containsKey(username)) {
            System.out.println("用户名不存在！");
            return;
        }
        System.out.println("请输入旧密码：");
        String oldPassword = scanner.nextLine();
        while(true) {
            if (!validateLogin(username, oldPassword)) {
                System.out.println("旧密码不正确！");
                System.out.println("请重新输入");
                oldPassword = scanner.nextLine();
            }else{
                break;
            }
        }
        System.out.println("请输入新密码（密码长度大于8个字符，必须是大小写字母、数字和标点符号的组合）：");
        String newPassword = scanner.nextLine();
        while(true) {
            if (!validatePassword(newPassword)) {
                System.out.println("新密码不符合要求！");
                System.out.println("请重新输入（密码长度大于8个字符，必须是大小写字母、数字和标点符号的组合）");
                newPassword = scanner.nextLine();
            }else{
                break;
            }
        }
        if (updatePassword(username, newPassword)) {
            System.out.println("密码修改成功！");
        } else {
            System.out.println("密码修改失败，请重试。");
        }
    }

    public static void loadUserData() {
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
    public static boolean validateLogin(String username, String password) {
        String storedPassword = userDatabase.get(username);
        return storedPassword != null && storedPassword.equals(password);
    }
    public static boolean validatePassword(String password) {
        // 密码长度大于8，包含大小写字母、数字和标点符号
        String passwordPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        return Pattern.matches(passwordPattern, password);
    }
    public static boolean updatePassword(String username, String newPassword) {
        try {
            File inputFile = new File("user_data.txt");
            File tempFile = new File("temp_user_data.txt");
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String storedUsername = parts[0];
                String storedPassword = parts[1];
                if (username.equals(storedUsername)) {
                    storedPassword = newPassword;
                    line = storedUsername + "," + storedPassword;
                }
                writer.write(line + "\n");
            }
            reader.close();
            writer.close();
            inputFile.delete();
            tempFile.renameTo(inputFile);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void ChongZhiUserPassword(){

    }







/***************************************/
    public void MasterPasswordSystem(){
        boolean flag=true;
        while(flag) {
            System.out.println("****************************");
            System.out.println("   欢迎来到管理员密码管理系统");
            System.out.println("   1.修改自身密码");
            System.out.println("   2.重置客户密码");
            System.out.println("   0.退出管理员密码管理系统");
            System.out.println("****************************");
            System.out.println("请输入数字（1，2，0）");
            int a;
            while (true) {
                try {
                    a = scanner.nextInt();
                    break;
                } catch (Exception e) {
                    System.out.println("输入错误，请重新输入");
                }
            }
            switch (a) {
                case 1:
                    XiGaiMasterPassword();
                    break;
                case 2:
                    ChongzhiUser1Password();
                    break;
                case 0:
                    flag=false;
                    System.out.println("退出管理员密码管理系统成功！！");
                    break;
            }
        }
    }
    public void XiGaiMasterPassword(){
        System.out.println("请输入新密码: ");
        scanner.nextLine();
        String newPassword = scanner.nextLine();
        if (updateMasterPassword(newPassword)) {
            System.out.println("密码修改成功！");
        } else {
            System.out.println("密码修改失败！");
        }
    }
    public boolean updateMasterPassword(String newPassword){
        try {
            // 读取现有的管理员数据，然后更新密码
            File file = new File("D:\\IDEAbianma\\Shop\\src\\master_data.txt");
            File tempFile = new File("D:\\IDEAbianma\\Shop\\src\\temp_master_data.txt");

            BufferedReader reader = new BufferedReader(new FileReader(file));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String storedUsername = parts[0];
                String storedPassword = parts[1];

                // 假设管理员用户名为 "jkl"
                if ("jkl".equals(storedUsername)) {
                    // 更新密码
                    storedPassword = newPassword;
                    line = storedUsername + "," + storedPassword;
                }

                writer.write(line + "\n");
            }

            reader.close();
            writer.close();

            // 删除原文件并将临时文件重命名为原文件名
            file.delete();
            tempFile.renameTo(file);

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void ChongzhiUser1Password(){
        String password="Jkl123456**";
        loadUserData();
        System.out.println("请输入用户名");
        String Username=scanner.nextLine();
        while (true) {
            if (!userDatabase.containsKey(Username)) {
                System.out.println("用户名不存在！");
                System.out.println("请重新输入用户名");
                Username=scanner.nextLine();
            }else{
                updatePassword(Username,password);
                System.out.println("重置密码成功！！");
                break;
            }
        }
    }


}
