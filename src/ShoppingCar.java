
import java.io.*;
import java.util.*;

public class ShoppingCar {
    Map<String, List<String>> userCarts = new HashMap<>();
    Scanner scanner=new Scanner(System.in);
    public ShoppingCar(){

    }
    public void ShopSystem() {
        boolean flag=true;
        while(flag) {
            System.out.println("**********************");
            System.out.println("     欢迎来到购物车");
            System.out.println("     1.添加商品到购物车");
            System.out.println("     2.移除购物车中的商品");
            System.out.println("     3.修改购物车中的商品");
            System.out.println("     4.付账");
            System.out.println("     5.查看购物历史");
            System.out.println("     0.退出购物车界面");
            System.out.println("**********************");
            System.out.println("************************");
            System.out.println("    商品信息如下");
            Goods goods=new Goods();
            goods.ShowAllGoods();
            System.out.println("************************");
            System.out.println("请输入数字（1、2、3、4、5、0）");
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
                    AddGoodsShop();
                    break;
                case 2:
                    DeleteGoodsShop();
                    break;
                case 3:
                    ChangeGoodsShop();
                    break;
                case 4:
                    Pay();
                    break;
                case 5:
                    SearchShop();
                    break;
                case 0:
                    flag=false;
                    System.out.println("退出购物车界面");
                    break;
            }
        }
    }

    public String getUserMessage(){
        String userFileName = "user_data.txt";
        try (BufferedReader userReader = new BufferedReader(new FileReader(userFileName))) {
            String line;
            while ((line = userReader.readLine()) != null) {
                String[] userInfo = line.split(",");
                String userId = userInfo[0].trim();
                userCarts.put(userId, new ArrayList<>());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        scanner.nextLine();
        System.out.println("请输入您的用户名或用户ID：");
        String userId = scanner.nextLine();
        if (!userCarts.containsKey(userId)) {
            System.out.println("未找到用户信息。");
        }else{
            return userId;
        }
        return "";
    }
    public void AddGoodsShop(){
        String userId= getUserMessage();
        List<String> cart = userCarts.get(userId);

        System.out.println("请输入要添加到购物车的商品编号：");
        String productNumber = scanner.nextLine();

        System.out.println("请输入要添加的商品数量：");
        int quantity = scanner.nextInt();

        String productionFileName = "goods.txt";
        List<String> productions = new ArrayList<>();

        try (BufferedReader productionReader = new BufferedReader(new FileReader(productionFileName))) {
            String line;
            while ((line = productionReader.readLine()) != null) {
                productions.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        boolean productFound = false;
        for (int i = 0; i < productions.size(); i++) {
            String[] productInfo = productions.get(i).split(",");
            String currentProductNumber = productInfo[0].trim();
            int currentQuantity = Integer.parseInt(productInfo[7].trim());
            if (currentProductNumber.equals(productNumber)) {
                productFound = true;
                if (currentQuantity >= quantity) {
                    String cartEntry = String.join(",", productInfo);
                    cart.add(cartEntry + "," + quantity);
                    productions.set(i, productInfo[0] + "," + productInfo[1] + "," + productInfo[2] + "," +
                            productInfo[3] + "," + productInfo[4] + "," + productInfo[5] + "," +
                            productInfo[6] + "," + (currentQuantity - quantity));
                    System.out.println("商品已成功添加到购物车！");
                } else {
                    System.out.println("库存不足，无法添加到购物车。");
                }
                break;
            }
        }

        if (!productFound) {
            System.out.println("未找到指定的商品。");
        }
        // Update production.txt file
        try (BufferedWriter productionWriter = new BufferedWriter(new FileWriter(productionFileName))) {
            for (String product : productions) {
                int quantityInStock = Integer.parseInt(product.split(",")[7].trim());
                if (quantityInStock > 0) {
                    productionWriter.write(product);
                    productionWriter.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String cartFileName = userId + "shop.txt";
        try (BufferedWriter cartWriter = new BufferedWriter(new FileWriter(cartFileName))) {
            for (String cartEntry : cart) {
                cartWriter.write(cartEntry);
                cartWriter.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean  showShop(String userId){
        String cartFileName = userId + "shop.txt";
        List<String> cart = new ArrayList<>();
        try (BufferedReader cartReader = new BufferedReader(new FileReader(cartFileName))) {
            String line;
            while ((line = cartReader.readLine()) != null) {
                cart.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (cart.isEmpty()) {
            System.out.println("购物车为空。");
            return false;
        }
        System.out.println("当前购物车内容：");
        for (String cartEntry : cart) {
            String[] productInfo = cartEntry.split(",");
            System.out.println("商品编号: " + productInfo[0]);
            System.out.println("商品名称: " + productInfo[1]);
            System.out.println("生产厂家: " + productInfo[2]);
            System.out.println("生产日期: " + productInfo[3]);
            System.out.println("型号: " + productInfo[4]);
            System.out.println("进货价: " + productInfo[5]);
            System.out.println("零售价格: " + productInfo[6]);
            System.out.println("数量: " + productInfo[7]);
            System.out.println("------------------------");
        }
        return true;
    }
    public void DeleteGoodsShop() {
        String userId = getUserMessage();
        List<String> cart = userCarts.get(userId);
        // Display current cart contents
        boolean flag = showShop(userId);
        if (flag) {
            System.out.println("请输入要删除的商品编号：");
            String productNumberToDelete = scanner.nextLine();
            // Prompt for confirmation before deleting
            System.out.println("警告：删除后无法恢复。是否确认删除？(yes/no)");
            String confirmation = scanner.nextLine();
            if (confirmation.equalsIgnoreCase("yes")) {
                boolean deleted = false;
                for (int i = 0; i < cart.size(); i++) {
                    String[] cartEntryInfo = cart.get(i).split(",");
                    String currentProductNumber = cartEntryInfo[0].trim();

                    if (currentProductNumber.equals(productNumberToDelete)) {
                        cart.remove(i);
                        deleted = true;
                        System.out.println("商品已成功从购物车删除。");
                        break;
                    }
                }

                if (!deleted) {
                    System.out.println("商品已成功从购物车删除。");
                }
            } else {
                System.out.println("删除操作已取消。");
            }

            // Update shop.txt file
            String cartFileName = userId + "shop.txt";
            try (BufferedWriter cartWriter = new BufferedWriter(new FileWriter(cartFileName))) {
                for (String cartEntry : cart) {
                    cartWriter.write(cartEntry);
                    cartWriter.newLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void ChangeGoodsShop(){

    }
    public void Pay(){

    }
    public void SearchShop(){
        String userId= getUserMessage();
        String fileName = userId + "shop.txt";
        System.out.println("**************************");
        System.out.println("购物历史如下");
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("**************************");
    }
}
