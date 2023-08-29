import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Customer {
    Scanner scanner=new Scanner(System.in);
    public Customer(){

    }

    public void CustomerSystem(){
        boolean flag=true;
        while(flag) {
            System.out.println("*******************");
            System.out.println("      客户管理界面");
            System.out.println("      1.列出所有客户信息");
            System.out.println("      2.删除客户信息");
            System.out.println("      3.查询客户信息");
            System.out.println("      0.退出客户管理界面");
            System.out.println("*******************");
            System.out.println("请输入数字（1、2、3、0）");
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
                    System.out.println("************************");
                    System.out.println("客户信息如下：");
                    ShowAllCustomer();
                    System.out.println("************************");
                    break;
                case 2:
                    DeleteCustomer();
                    break;
                case 3:
                    SearchCustomer();
                    break;
                case 0:
                    flag=false;
                    System.out.println("退出客户管理界面");
                    break;
            }
        }
    }
    public void ShowAllCustomer(){
        String fileName = "customer.txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    public void DeleteCustomer() {
        String fileName = "customer.txt";
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            System.out.println("请输入要删除的客户编号：");
            scanner.nextLine();
            String productNumberToDelete = scanner.nextLine();
            System.out.println("警告：删除后无法恢复！确认是否继续？（输入 'yes' 确认）");
            String confirmation = scanner.nextLine();
            if (!confirmation.equalsIgnoreCase("yes")) {
                System.out.println("删除操作已取消。");
                return;
            }
            String line;
            while ((line = reader.readLine()) != null) {
                String[] productInfo = line.split(",");
                String productNumber = productInfo[0].trim();
                if (!productNumber.equals(productNumberToDelete)) {
                    lines.add(line);
                }
            }
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
                for (String updatedLine : lines) {
                    writer.write(updatedLine);
                    writer.newLine();
                }
                System.out.println("客户信息已成功删除！");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    public void SearchCustomer(){
        String fileName = "customer.txt";
        List<String> products = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                products.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        System.out.println("请选择查询类型：");
        System.out.println("1. 客户ID或者客户的用户名进行查询");
        System.out.println("2. 查询所有客户的信息");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Clear the newline character
        List<String> queryResults = new ArrayList<>();
        if (choice == 1) {
            System.out.println("客户ID或者客户的用户名进行查询：");
            String query = scanner.nextLine().toLowerCase();
            for (String product : products) {
                if (product.toLowerCase().contains(query)) {
                    queryResults.add(product);
                }
            }
        } else if (choice == 2) {
            ShowAllCustomer();
        }
        if (!queryResults.isEmpty()) {
            System.out.println("查询结果：");
            for (String result : queryResults) {
                System.out.println(result);
            }
        } else {
            System.out.println("未找到该客户。");
        }
    }
}
