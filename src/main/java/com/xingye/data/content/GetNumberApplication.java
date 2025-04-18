package com.xingye.data.content;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;

@SpringBootApplication
@MapperScan("com.xingye.data.content.dao")
public class GetNumberApplication {
    private static ConfigurableApplicationContext context;
    private static TrayIcon trayIcon;

    public static void main(String[] args) {
        // 初始化Spring上下文
        context = SpringApplication.run(GetNumberApplication.class, args);

        // 初始化系统托盘
        initSystemTray();
    }

    private static void initSystemTray() {
        // 检查系统托盘支持
        if (!SystemTray.isSupported()) {
            System.out.println("系统托盘不支持");
            return;
        }

        // 设置GUI相关系统属性
        System.setProperty("java.awt.headless", "false");
        System.setProperty("apple.awt.UIElement", "true");

        // 加载托盘图标
        Image trayImage = loadTrayIcon();

        // 创建弹出菜单
        PopupMenu popup = createPopupMenu();

        // 创建托盘图标
        trayIcon = new TrayIcon(trayImage, "数据服务系统", popup);
        trayIcon.setImageAutoSize(true);

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
        }
    }

    private static Image loadTrayIcon() {
        try {
            // 优先加载PNG格式，如果失败尝试ICO
            Image image = Toolkit.getDefaultToolkit().getImage(
                    GetNumberApplication.class.getResource("/tray-icon.png"));

            // 验证图片是否加载成功
            if (image.getWidth(null) == -1) {
                throw new Exception("PNG图标加载失败，尝试ICO格式");
            }
            return image;
        } catch (Exception e) {
            try {
                return Toolkit.getDefaultToolkit().getImage(
                        GetNumberApplication.class.getResource("/tray-icon.ico"));
            } catch (Exception ex) {
                showErrorDialog("图标加载失败: " + ex.getMessage());
                return null;
            }
        }
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
            // 备用方案：使用命令行打开
            Runtime runtime = Runtime.getRuntime();
            if (System.getProperty("os.name").toLowerCase().contains("win")) {
                runtime.exec("rundll32 url.dll,FileProtocolHandler " + url);
            } else {
                runtime.exec("xdg-open " + url);
            }
        } catch (Exception ex) {
            showErrorDialog("无法打开浏览器: " + ex.getMessage());
        }
    }

    private static void shutdownApplication() {
        // 关闭Spring上下文
        if (context != null && context.isRunning()) {
            context.close();
        }

        // 移除托盘图标
        SystemTray.getSystemTray().remove(trayIcon);

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