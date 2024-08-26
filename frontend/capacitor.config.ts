import type { CapacitorConfig } from '@capacitor/cli';

const config: CapacitorConfig = {
  appId: 'com.example.app',
  appName: 'testReact',
  webDir: './webApp',
  plugins: {
    CapacitorHttp: {
      enabled: true,
    },
  }
};

export default config;
