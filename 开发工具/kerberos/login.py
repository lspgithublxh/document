import subprocess
import time
def Main():
    tab = crt.GetScriptTab()
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
    # time.sleep(2)
    # tab.Screen.Send("{pwd}".format(pwd='Lsp2019*999999'))
    # tab.Screen.WaitForStrings('[work(lishaoping)@bjm68-18-214 ~]')
    return
 
Main()