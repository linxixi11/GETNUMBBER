package com.xingye.data.content;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.net.URI;
import java.net.URL;

@SpringBootApplication
@MapperScan("com.xingye.data.content.dao")
public class GetNumberApplication {
    private static ConfigurableApplicationContext context;
    private static TrayIcon trayIcon;

    public static void main(String[] args) {
        System.setProperty("java.awt.headless", "false"); // 必须放在Spring启动前
        // 初始化Spring上下文
        context = SpringApplication.run(GetNumberApplication.class, args);


        // 改用事件调度线程初始化
        SwingUtilities.invokeLater(() -> initSystemTray());
    }

    private static void initSystemTray() {
        // 检查系统托盘支持
        if (!SystemTray.isSupported()) {
            System.out.println("系统托盘不支持");
            return;
        }

        // 设置GUI相关系统属性（提前到事件调度线程中设置）
        System.setProperty("java.awt.headless", "false");
        System.setProperty("apple.awt.UIElement", "true");

        // 加载托盘图标
        Image trayImage = loadTrayIcon();
        if (trayImage == null) {
            showErrorDialog("图标加载失败，使用默认应急图标");
            trayImage = createFallbackIcon(); // 确保有默认图标
        }

        // 创建弹出菜单
        PopupMenu popup = createPopupMenu();

        // 创建托盘图标（明确指定图标尺寸，解决部分系统尺寸适配问题）
        trayIcon = new TrayIcon(trayImage.getScaledInstance(32, 32, Image.SCALE_SMOOTH), "数据服务系统", popup);
        trayIcon.setImageAutoSize(true);
        trayIcon.setToolTip("数据服务系统"); // 添加工具提示

        // 添加双击事件
        trayIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    openBrowser("http://localhost:8080");
                }
            }
        });

        try {
            // 添加到系统托盘
            SystemTray.getSystemTray().add(trayIcon);
        } catch (AWTException e) {
            showErrorDialog("无法添加托盘图标: " + e.getMessage());
            e.printStackTrace(); // 添加异常堆栈打印便于调试
        }
    }

    private static Image loadTrayIcon() {
        try {
            // 检查资源是否存在（使用绝对路径检查）
            URL pngUrl = GetNumberApplication.class.getResource("/aaa.png");
            URL icoUrl = GetNumberApplication.class.getResource("/aaa.ico");

            // 优先加载PNG，兼容不同系统图标格式
            if (pngUrl != null) {
                return loadImageFromUrl(pngUrl);
            } else if (icoUrl != null) {
                return loadImageFromUrl(icoUrl);
            } else {
                throw new FileNotFoundException("tray-icon.png和tray-icon.ico均未找到");
            }
        } catch (Exception e) {
            System.out.println("图标加载失败: " + e.getMessage());
            return null; // 允许返回null以便使用应急图标
        }
    }

    // 统一图片加载方法，添加异常处理和加载状态检查
    private static Image loadImageFromUrl(URL url) throws Exception {
        Image image = Toolkit.getDefaultToolkit().getImage(url);
        MediaTracker tracker = new MediaTracker(new Component() {});
        tracker.addImage(image, 0);
        tracker.waitForAll(); // 等待图片加载完成

        if (tracker.isErrorAny()) {
            throw new Exception("图片加载错误");
        }
        return image;
    }

    // 生成应急图标（使用更通用的32x32尺寸）
    private static Image createFallbackIcon() {
        BufferedImage img = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(new Color(0, 122, 255)); // 使用更明显的蓝色
        g.fillRoundRect(0, 0, 32, 32, 8, 8); // 圆角矩形更美观
        g.setColor(Color.WHITE);
        g.setFont(new Font("SansSerif", Font.BOLD, 16));
        g.drawString("💻", 4, 22); // 添加图标字符
        g.dispose();
        return img;
    }

    private static PopupMenu createPopupMenu() {
        PopupMenu menu = new PopupMenu();

        // 打开页面菜单项
        MenuItem openItem = new MenuItem("打开控制台");
        openItem.addActionListener(e -> openBrowser("http://localhost:8080"));
        menu.add(openItem);

        // 分隔线
        menu.addSeparator();

        // 退出菜单项
        MenuItem exitItem = new MenuItem("退出系统");
        exitItem.addActionListener(e -> shutdownApplication());
        menu.add(exitItem);

        return menu;
    }

    private static void openBrowser(String url) {
        try {
            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                if (desktop.isSupported(Desktop.Action.BROWSE)) {
                    desktop.browse(new URI(url));
                    return;
                }
            }
            // 备用方案：使用命令行打开（添加Windows路径转义处理）
            String os = System.getProperty("os.name").toLowerCase();
            String command = os.contains("win") ?
                    "rundll32 url.dll,FileProtocolHandler " + url.replace(" ", "\\ ") : // 处理路径空格
                    "xdg-open " + url;
            Runtime.getRuntime().exec(command);
        } catch (Exception ex) {
            showErrorDialog("无法打开浏览器: " + ex.getMessage());
            ex.printStackTrace(); // 添加异常堆栈打印
        }
    }

    private static void shutdownApplication() {
        // 关闭Spring上下文
        if (context != null && context.isRunning()) {
            context.close();
        }

        // 移除托盘图标（添加存在性检查）
        if (trayIcon != null && SystemTray.isSupported()) {
            SystemTray.getSystemTray().remove(trayIcon);
        }

        // 退出系统
        System.exit(0);
    }

    private static void showErrorDialog(String message) {
        JOptionPane.showMessageDialog(null,
                message,
                "系统错误",
                JOptionPane.ERROR_MESSAGE);
    }
}
