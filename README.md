# Dropp
[![Java CI with Maven](https://github.com/Overdrive141/Dropp/actions/workflows/maven.yml/badge.svg)](https://github.com/Overdrive141/Dropp/actions/workflows/maven.yml)

---
## Requirements
The following requirements must be installed in your environment before continuing:
- Shell: [Bash (Windows)](https://itsfoss.com/install-bash-on-windows/)
- Languages & Framework
  - [Flutter (incl. Dart)](https://flutter.dev/docs/get-started/install)
  - [Maven](https://maven.apache.org/)
- Emulation: [Android SDK (Android Studio)](https://developer.android.com/studio/install)
- IDE: [Visual Studio Code](https://code.visualstudio.com/download)
- Source Control: [Git](https://git-scm.com/book/en/v2/Getting-Started-Installing-Git)
- CLI
  - [Firebase](https://firebase.google.com/docs/cli)
  - [GitHub](https://cli.github.com/)
  - [Java](https://www.oracle.com/java/technologies/downloads/)
---

## Getting Started
Let's prepare your development environment

> Note: PowerShell on Windows is not supported. Please use [bash](https://itsfoss.com/install-bash-on-windows/)
### __Clone the repository in your local machine__

Create a development workspace

Clone the code repository
```bash
git clone https://github.com/Overdrive141/Dropp.git
```

Navigate to the `dropp` directory

```bash
cd dropp
```
## Contributing

### __Building__
To build the Flutter app in the repository use the `flutter` `build` command
```bash
flutter build <platform-name>(apk | ios) --dart-define=GOOGLE_MAPS_KEY=<maps-key>
```

### __Testing__
Shortly it will be as simple as:
```bash
flutter test
```

### __Conventions__

Branch names should follow the `[scope]/[feature]-[description]` convention, where
- `scope` is `fix`, `feat`/`feature`, `refactor` or `chore`
- `feature` is the major component affected, e.g. `backend` or `firebase`, and
- `description` is a short, clear summary of the issue/ticket to which this branch refers

Pull requests should be created as `Draft` until they are ready for review
- prevents unnecessary reviews when work is in progress
- reduces re-reviews cycles

Always update the Pull Request `Description` and `Summary` before merge
- changes during the review cycle may change the implementation and changes, this keeps the commit information up to date with the changes

Always `Squash and Merge` a Pull Request
- this cleans up the commit history of the `main` branch
---

#### Auto-remove unused imports on Save

1. Open `Command Palette` and search for `Editor`
3. Scroll down to `Editor: Code actions on Save`
4. Click on `Edit in settings.json`
5. Update `editor.codeActionsOnSave` block to the following

```json
"editor.codeActionsOnSave": {
    "source.organizeImports": true
},
```
---
### __Tips & Tricks__
#### __zsh__
If you are using zsh (as opposed to bash):
- install the [oh-my-zsh](https://ohmyz.sh/#install) framework
- try the agnoster theme by adding `ZSH_THEME='agnoster'` into `~/.zshrc`
- add `git`, `github`, `yarn` & `flutter` to plugins() in `~/.zshrc` for autocomplete/aliases
- install [fzf](https://github.com/junegunn/fzf) fuzzy reverse search and add `[ -f ~/.fzf.zsh ] && source ~/.fzf.zsh` to the end of your `~/.zshrc`
#### __tmux__
tmux is a terminal multi-plexer; it allows your shells to persist after you close your terminal and allows you to have multiple terminals open simulataneously
- install [tmux](https://github.com/tmux/tmux/wiki)
- install tmux profile manager [tmuxinator](https://github.com/tmuxinator/tmuxinator)


### __Running locally using Emulation__
To run the app locally an emulator is required. If you have not configured an emulator,
launch Android Studio and [add a Virtual Device using AVD](https://developer.android.com/studio/run/managing-avds).

_Note: Ensure that the Virtual Device you create includes the PlayStore (!)_

Once you have created a Virtual Device, you no longer need Android Studio. Navigate back to Visual Studio Code:
1. Open `Command Palette` and search for `Flutter: Select Device`
2. Select the Virtual Device you added earlier in Android Studio AVD
3. Navigate to the `Run & Debug` view
   1. Try the shortcut `Ctrl` + `Shift` + `D`, or
   2. Click on `View` -> `Run`
4. On the top dropdown select an app to run, e.g. `Dropp (Development)`
5. Click the green Play button
