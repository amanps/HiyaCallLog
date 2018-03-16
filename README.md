# HiyaCallLog

### The Main Call Log

<img src="/images/Screenshot_20180316-141206.png" width="256">

### Permission Request Flow

<img src="/images/Screenshot_20180316-141032.png" width="256"> <img src="/images/Screenshot_20180316-141042.png" width="256"> <img src="/images/Screenshot_20180316-141048.png" width="256">

### App Architecture:

I'm following the MVP architecture.

```bash
├── app
│   ├── model
│       ├── CallLogItem
│   ├── telephony
│       ├── PhoneCallStateManager
│       ├── PhoneStateReceiver
│   ├── ui
│       ├── base
│         ├── BasePresenter
│         ├── (I)BaseView
│         ├── (I)Presenter
│       ├── main
│         ├── MainActivity
│         ├── MainPresenter
│         ├── CallListAdapter
│         ├── (I)MainMvpPresenter
│         ├── (I)MainView
│   ├── Utils
```
