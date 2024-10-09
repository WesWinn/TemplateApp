package kmp.template.app

// Kind of unused boilerplate but useful best practice to keep in
interface Platform {
    val name: String
}

expect fun getPlatform(): Platform