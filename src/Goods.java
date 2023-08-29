import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Goods {
    Scanner scanner=new Scanner(System.in);
    public Goods(){
    }
    public void GoodsSystem(){
        boolean flag=true;
        while(flag) {
            System.out.println("*******************");
            System.out.println("      商品管理界面");
            System.out.println("      1.显示商品信息");
            System.out.println("      2.添加商品");
            System.out.println("      3.删除商品");
            System.out.println("      4.修改商品信息");
            System.out.println("      5.查询商品信息");
            System.out.println("      0.退出商品管理界面");
            System.out.println("*******************");
            System.out.println("请输入数字（1，2，3，4，5，0）");
            int a = 0;
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
                    ShowAllGoods();
                    break;
                case 2:
                    AddGoods();
                    break;
                case 3:
                    DeleteGoods();
                    break;
                case 4:
                    ChangeGoods();
                    break;
                case 5:
                    SearchGoods();
                    break;
                case 0:
                    flag=false;
                    System.out.println("退出商品管理界面");
                    break;
            }
        }
    }
    public void ShowAllGoods(){
        String fileName = "goods.txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void AddGoods(){
        String fileName = "goods.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("请输入要添加的商品的信息：（商品编号、商品名称、生产厂家、生产日期、型号、进货价、零售价格、数量。）");
            String content = scanner.nextLine();
           // writer.newLine(); // 添加新行
            writer.write("\n" + content);
            System.out.println("内容已成功追加到文件！");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void DeleteGoods(){
        String fileName = "goods.txt";
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            System.out.println("请输入要删除的商品编号：");
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
                System.out.println("商品信息已成功删除！");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ChangeGoods(){
        String fileName = "goods.txt";
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
            System.out.println("请输入要修改的商品名称：");
            scanner.nextLine();
            String productName = scanner.nextLine().toLowerCase();
            boolean found = false;
            for (int i = 0; i < products.size(); i++) {
                String product = products.get(i);
                if (product.toLowerCase().contains(productName)) {
                    System.out.println("请输入新的商品信息（格式：商品编号,商品名称,生产厂家,生产日期,型号,进货价,零售价格,数量）：");
                    String newProductInfo = scanner.nextLine();
                    products.set(i, newProductInfo);
                    found = true;
                    break;
                }
            }
            if (found) {
                System.out.println("商品信息已成功修改！");
            } else {
                System.out.println("未找到要修改的商品。");
            }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (String product : products) {
                writer.write(product);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void SearchGoods(){
        String fileName = "goods.txt";
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
        System.out.println("1. 单独查询");
        System.out.println("2. 组合查询");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Clear the newline character
        List<String> queryResults = new ArrayList<>();
        if (choice == 1) {
            System.out.println("请输入商品名称、生产厂家或零售价格进行查询：");
            String query = scanner.nextLine().toLowerCase();
            for (String product : products) {
                if (product.toLowerCase().contains(query)) {
                    queryResults.add(product);
                }
            }
        } else if (choice == 2) {
            System.out.println("请输入查询条件，例如：生产厂家、零售价格（格式：'联想公司 1000'）：");
            String query = scanner.nextLine().toLowerCase();
            String[] queryParts = query.split(" ");
            String manufacturerQuery = queryParts[0];
            double priceQuery = Double.parseDouble(queryParts[1]);
            for (String product : products) {
                String[] productInfo = product.split(",");
                String manufacturer = productInfo[2].trim();
                double retailPrice = Double.parseDouble(productInfo[6].trim());
                if (manufacturer.equalsIgnoreCase(manufacturerQuery) && retailPrice >= priceQuery) {
                    queryResults.add(product);
                }
            }
        }
        if (!queryResults.isEmpty()) {
            System.out.println("查询结果：");
            for (String result : queryResults) {
                System.out.println(result);
            }
        } else {
            System.out.println("未找到符合条件的商品。");
        }
    }

}
