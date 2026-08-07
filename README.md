# AudioSwitch — EqualizedEvo

An in-progress, ad-free, open-source local music player for Android, built with Kotlin and Jetpack Compose. The long-term goal is a player with 3D spatial audio, a built-in equalizer, and a system-wide audio output switch — this README describes what's actually implemented today versus what's still planned.

## Current status: work in progress

This is an early-stage personal project. The core "play music from your device" experience works; the headline features from the project's vision (spatial audio, equalizer, output switching) are **not yet implemented** — they exist only as placeholder screens/enum values in the code.

### Implemented today
- **Local library scan** — reads all audio tracks visible to `MediaStore` (title, artist, album, duration, album art, file path) via `FetchMusic`.
- **Playback** — play/pause, skip to next/previous track, and seek, powered by AndroidX Media3 `ExoPlayer`.
- **Now-playing UI** — a compact mini-player bar and a full-screen player, connected with Compose shared-element transitions; album art loaded with Coil.
- **Songs list screen** — a scrollable list of all detected tracks with tap-to-play; a "No Songs Found" empty state when the library is empty.
- **Runtime permission handling** — requests `READ_MEDIA_AUDIO` (Android 13+) or `READ_EXTERNAL_STORAGE` on older versions before scanning the library.
- **Tabbed home screen** — swipeable tabs ("Songs" / "Favourites") using `HorizontalPager`, though only the Songs tab currently has content.
- Dependency injection via **Hilt** for the player and music-fetching classes, following an MVVM structure (`SongsViewModel` + `UIState`).

### Scaffolded but not functional yet
- "Favourites" tab and a search action are present in the UI but currently do nothing (no favourites persistence, no search logic wired up).
- The song row's overflow menu lists "Like / Add to Queue / Add to Playlist / Delete" actions, but none of them are implemented (`TODO` in code).
- `screenState` includes `PLAYLISTS`, `SETTINGS`, `EQUALIZER`, `SWITCH`, and `SPATIAL_AUDIO` states, and `SwipeScreen` has placeholder pager pages for "Spatial Audio" and "Equalizer" — these are stubs with no real screens behind them yet.
- The Gradle build already pulls in `androidx.mediarouter` and Google's Cast framework in anticipation of an output-switching feature, but no code currently uses them.

### Planned (not started)
- 3D / spatial audio playback (notes in the repo point at Android's `Spatializer` API).
- A real equalizer (bass/treble/band control), likely via `android.media.audiofx.AudioEffect`.
- System-wide audio output switching (e.g. speaker ⇄ Bluetooth ⇄ cast) via `MediaRouter`/Cast.
- Playlist creation/management and a working Favourites/Search flow.

## Tech stack

- **Language:** Kotlin
- **UI:** Jetpack Compose (Material 3), Compose Navigation, shared-element transitions (`SharedTransitionLayout`)
- **Playback:** AndroidX Media3 `ExoPlayer` (+ media3-ui, media3-exoplayer-dash)
- **DI:** Dagger Hilt
- **Image loading:** Coil
- **Data source:** Android `MediaStore` (on-device audio scan) — no backend/server component
- **Build system:** Gradle (Kotlin DSL), Android Gradle Plugin
- Min SDK 24, target/compile SDK 34

## Project structure

```
app/src/main/java/com/example/audioswitch_equalizedevo/
├── App.kt                     # Application class (Hilt entry point)
├── data/
│   ├── FetchMusic.kt           # Scans MediaStore for audio tracks
│   ├── Songs.kt                 # Song data model
│   ├── ExoPlayer1.kt            # ExoPlayer wrapper (play/pause/next/prev)
│   └── provider.kt              # Hilt modules for ExoPlayer/FetchMusic
└── ui/
    ├── activities/MainActivity.kt   # Permission handling + Compose NavHost
    ├── viewModels/SongsViewModel.kt # Playback + song list state (MVVM)
    ├── screens/                     # HomeScreen, SwipeScreen, SongScreen, PlayerScreens
    └── theme/                       # Compose theme, colors, typography
```

## Build & run

Requires Android Studio (Koala or newer recommended) with JDK 17 and the Android SDK (API 34) installed.

```bash
git clone https://github.com/asp616848/AudioSwitch---EqualizedEvo.git
cd AudioSwitch---EqualizedEvo
```

Then either open the project in Android Studio and run the `app` configuration on a device/emulator (API 24+), or build from the command line:

```bash
./gradlew assembleDebug
# APK output: app/build/outputs/apk/debug/app-debug.apk
```

On first launch, grant the audio/storage permission prompt so the app can scan your device's music library.

## Contributing

This is an actively evolving project — issues and pull requests are welcome, especially around the planned equalizer, spatial audio, and output-switching features noted above.
