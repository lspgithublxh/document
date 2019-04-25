crt.Session.Connect("/S 10.48.210.41")
import time


def Main(i):
    # tab = crt.GetScriptTab()
    tab = crt.GetTab(i)
    if tab.Session.Connected != True:
        crt.Dialog.MessageBox("Session Not Connected")
        return
    tab.Screen.Synchronous = True

    tab.Screen.WaitForStrings('[Count:3 page:1/1 select number|ip]:', 5)
    vc = 'w'
    tab.Screen.Send("{vc}".format(vc=vc))
    tab.Screen.WaitForStrings('input ip:', 3)
    tab.Screen.Send("{pwd}\n".format(pwd='10.8.18.214'))
    tab.Screen.WaitForStrings('input permsion|select number:', 3)
    tab.Screen.Send("{pwd}\r".format(pwd='2'))
    tab.Screen.WaitForStrings('Password for lishaoping@58OS.ORG:', 3)
    # time.sleep(2)
    # tab.Screen.Send("{pwd}".format(pwd='Lsp2019*999999'))
    # tab.Screen.WaitForStrings('Password for lishaoping@58OS.ORG', 3)
    # time.sleep(2)
    tab.Screen.Send("{pwd}\r\n".format(pwd='Lsp2019*999999'))
    time.sleep(2)
    # tab.Screen.Send("{pwd}".format(pwd='Lsp2019*999999'))
    # tab.Screen.WaitForStrings('[work(lishaoping)@bjm68-18-214 ~]$')
    tab.Screen.Send("cd /opt\r\n");
    try:
        tab.Screen.Send("mkdir ttt\r\n");
    except Exception as e:
        print e
    tab.Screen.Send("cd  /opt/ttt\r\n");
    time.sleep(1)
    try:
        crt.FileTransfer.ZmodemUploadAscii = False
        crt.FileTransfer.AddToZmodemUploadList("D:\\software\\DLeadFollowTest-0.0.1-SNAPSHOT.jar")
        crt.Screen.Send("rz -e\n")

    except Exception as e:
        print e
    time.sleep(2)
    tab.Screen.WaitForStrings('[work(lishaoping)@bjm68-18-214 ttt]$', 3)
    crt.Screen.Send('java -Djava.ext.dirs=./ -jar DLeadFollowTest-0.0.1-SNAPSHOT.jar\r\n')
    tab.Screen.WaitForStrings('[work(lishaoping)@bjm68-18-214 ttt]$', 3)
    return


Main(1)
# crt.Session.Connect("/RLOGIN 10.48.210.41")