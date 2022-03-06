package com.rijaldev.inmovies.utils

import java.util.concurrent.Executor
class AppExecutorsTestUtil: Executor {
    override fun execute(command: Runnable) {
        command.run()
    }
}