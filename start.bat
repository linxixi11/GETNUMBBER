#NoTrayIcon
SetWorkingDir %A_ScriptDir%  ; 设置工作目录为脚本所在目录

; ====================== 配置部分（用户需修改） ======================
JAR_FILE := "GETNUMBER-0.0.1-SNAPSHOT.jar"  ; JAR文件名（与脚本同目录）
JRE_FOLDER := "jre"                         ; 集成JRE文件夹名称（与脚本同目录）
PORT := "8080"                              ; JAR服务端口
; ====================== 自动检测JRE路径 ======================
if InStr(FileExist(".\%JRE_FOLDER%\bin\java.exe"), "D")
    JRE_PATH := A_ScriptDir . "\" . JRE_FOLDER
else
    JRE_PATH := ""  ; 若用户有系统JRE，后续通过环境变量查找

; 托盘图标菜单
Menu TrayMenu, Add, 启动服务(&S), StartJar
Menu TrayMenu, Add, 关闭服务(&T), StopJar
Menu TrayMenu, Add, 退出(&X), ExitApp
Menu TrayMenu, Add
Menu TrayMenu, Add, 关于(&A), ShowAbout

; 创建托盘图标（使用Java图标，可替换为自定义ico）
TrayIcon := "Java.ico"  ; 需与脚本同目录，可从资源包获取
if !InStr(FileExist(TrayIcon), "F")
    TrayTip,, "警告：未找到托盘图标文件", 2
TrayMenu, Show, , , TrayIcon  ; 显示托盘图标

; 启动服务
StartJar:
    if (ProcessExist("java.exe", "JAR服务")) {
        TrayTip,, "服务已启动", 1
        return
    }
    ; 构建Java可执行路径
    JAVA_EXE := JRE_PATH . "\bin\java.exe"
    if !InStr(FileExist(JAVA_EXE), "F") {
        TrayTip,, "错误：未找到JRE环境", 2
        return
    }
    ; 后台启动JAR（无控制台窗口）
    Run *NoConsole "%JAVA_EXE%" -Dfile.encoding=UTF-8 -jar "%A_ScriptDir%\%JAR_FILE%" , , "JAR服务"
    ; 自动打开浏览器
    Run "http://localhost:%PORT%"
    TrayTip,, "服务启动成功", 1
return

; 关闭服务
StopJar:
    if !ProcessExist("java.exe", "JAR服务") {
        TrayTip,, "服务未运行", 1
        return
    }
    ; 终止所有关联的Java进程（精准匹配启动时的标签）
    Process, Close, , "JAR服务"
    TrayTip,, "服务已关闭", 1
return

; 显示关于信息
ShowAbout:
    MsgBox, 64, JAR服务管理工具, `
    版本：1.0`n`
    功能：通过系统托盘管理JAR服务`n`
    支持集成JRE环境`n`
    右键菜单操作`n`
    `n`
    开发者：豆包编程助手
return

; 进程存在检测函数
ProcessExist(ProcessName, WinTitle="") {
    Process, Exist, %ProcessName%
    if (ErrorLevel = 0) {
        if (WinTitle != "") {
            WinGetTitle, CurrentTitle, ahk_pid %ProcessID%
            if InStr(CurrentTitle, WinTitle)
                return true
        } else {
            return true
        }
    }
    return false
}

; 退出时清理托盘
ExitApp:
    TrayMenu, DeleteAll
    TrayTip,, "程序已退出", 1
    ExitApp
return

; 托盘图标双击事件（可自定义）
TrayClick:
    Run "http://localhost:%PORT%"  ; 双击打开浏览器
return
    