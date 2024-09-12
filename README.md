### Module Graph

```mermaid
%%{
  init: {
    'theme': 'neutral'
  }
}%%

graph LR
  subgraph :data
    :data:remote["remote"]
    :data:local["local"]
  end
  subgraph :driver
    :driver:rest["rest"]
    :driver:localdb["localdb"]
    :driver:config["config"]
  end
  subgraph :features
    :features:home["home"]
    :features:search["search"]
    :features:saved["saved"]
    :features:preview["preview"]
    :features:collection["collection"]
  end
  :data:remote --> :data
  :data:remote --> :driver:rest
  :features:home --> :app
  :features:search --> :app
  :app --> :navigator
  :app --> :injection
  :app --> :domain
  :app --> :features:home
  :app --> :features:search
  :app --> :features:saved
  :app --> :features:preview
  :app --> :features:collection
  :features:preview --> :app
  :data:local --> :data
  :data:local --> :driver:localdb
  :data:local --> :driver:config
  :features:saved --> :app
  :data --> :domain
  :injection --> :data
  :injection --> :domain
  :injection --> :data:local
  :injection --> :data:remote
  :features:collection --> :app
```
## Built With
* [Kotlin](https://kotlinlang.org/)
* [Androidx](https://developer.android.com/jetpack/androidx)
* [View Binding](https://developer.android.com/topic/libraries/view-binding)
* [Glide](https://github.com/bumptech/glide)
* [Retrofit](https://github.com/square/retrofit)
* [OKHTTP](https://github.com/square/okhttp)
* [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)
* [LiveData](https://developer.android.com/topic/libraries/architecture/livedata)
* [Room](https://developer.android.com/jetpack/androidx/releases/room)
* [FCM](https://firebase.google.com/docs/cloud-messaging)

<!-- CONTRIBUTING -->
## Contributing

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request



<!-- LICENSE -->
## License

Distributed under the MIT License. See `LICENSE` for more information.



<!-- CONTACT -->
## Contact

Anang Kurniawan - [@anang_kur](https://twitter.com/anang_kur) - anangk97@gmail.com

## Special Thanks
* Beautiful design by Setyono Dwi Utomo - [Twitter](https://twitter.com/setyonodwi) - [Instagram](https://instagram.com/setyonodwi)
* Image resources from [unsplash](https://unsplash.com/)

<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[contributors-shield]: https://img.shields.io/github/contributors/othneildrew/Best-README-Template.svg?style=flat-square
[contributors-url]: https://github.com/othneildrew/Best-README-Template/graphs/contributors
[forks-shield]: https://img.shields.io/github/forks/othneildrew/Best-README-Template.svg?style=flat-square
[forks-url]: https://github.com/othneildrew/Best-README-Template/network/members
[stars-shield]: https://img.shields.io/github/stars/othneildrew/Best-README-Template.svg?style=flat-square
[stars-url]: https://github.com/othneildrew/Best-README-Template/stargazers
[issues-shield]: https://img.shields.io/github/issues/othneildrew/Best-README-Template.svg?style=flat-square
[issues-url]: https://github.com/othneildrew/Best-README-Template/issues
[license-shield]: https://img.shields.io/github/license/othneildrew/Best-README-Template.svg?style=flat-square
[license-url]: https://github.com/othneildrew/Best-README-Template/blob/master/LICENSE.txt
[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=flat-square&logo=linkedin&colorB=555
[linkedin-url]: https://linkedin.com/in/othneildrew