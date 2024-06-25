const {app , BrowserWindow} = require('electron')
const path = require('node:path')

app.disableHardwareAcceleration();

app.on('ready', () => {

const createWindow = () => {
    const win = new BrowserWindow({
        width:900,
        height:700,
    })
    win.loadFile('webApp/index.html')

    // win.webContents.openDevTools();
}

app.whenReady().then(()=> {
    createWindow()

    app.on('activate', () => {
        if (BrowserWindow.getAllWindows().length === 0) createWindow()
      })
})

app.on('window-all-closed', () => {
    if (process.platform !== 'darwin') app.quit()
  })

});