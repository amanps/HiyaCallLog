# HiyaCallLog

### The Main Call Log

<img src="/images/Screenshot_20180316-141206.png" width="256">

### Permission Request Flow

<img src="/images/Screenshot_20180316-141032.png" width="256"> <img src="/images/Screenshot_20180316-141042.png" width="256">

<img src="/images/Screenshot (Mar 16, 2018 3_44_59 PM).png" width="256"> <img src="/images/Screenshot_20180316-141048.png" width="256">

### App Architecture

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
├── test
│   ├── CallLogModelTest
```

### Notes

I didn't use pull-to-refresh to update the call log. 

I set up a state transition manager `PhoneCallStateManager` to receive broadcasts for new calls (incoming/outgoing) from `PhoneStateReceiver`. Using the observer pattern, this registers new calls on a `LiveData<>` object which any UI element can observe. Currently I'm logging these calls.

<img src="/images/Screen Shot 2018-03-16 at 12.27.27 PM.png" width="1024">

Although I have this set up in my code, I didn't use it to update the displayed CallLog dynamically.

Instead, I used a `CursorLoader` to load CallLog data. `CursorLoader`s automatically register a `ContentObserver` on the underlying data source which triggers a reload when the data changes. So, updates to the CallLog are observed and the `onLoadFinished` callback is triggered, which posts an update to the data held by the `RecyclerView`.~
