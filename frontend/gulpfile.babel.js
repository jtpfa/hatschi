import autoprefixer from 'autoprefixer'
import del from 'del'
import { dest, parallel, series, src, watch } from 'gulp'
import cleancss from 'gulp-clean-css'
import finclude from 'gulp-file-include'
import cmq from 'gulp-group-css-media-queries'
import gulpif from 'gulp-if'
import imagemin from 'gulp-imagemin'
import postcss from 'gulp-postcss'
import sass from 'gulp-sass'
import sourcemaps from 'gulp-sourcemaps'
import named from 'vinyl-named'
import webpack from 'webpack-stream'
import yargs from 'yargs'

const PROD = yargs.argv.prod

const paths = {
    dist: {
        style: 'dist/css',
        js: 'dist/js',
        img: 'dist/img',
    },
    src: {
        style: 'src/scss/',
        js: 'src/js/',
        img: 'src/img/',
    },
}

export const styles = () => {
    return src(`${paths.src.style}main.scss`)
        .pipe(gulpif(!PROD, sourcemaps.init()))
        .pipe(sass().on('error', sass.logError))
        .pipe(gulpif(PROD, postcss([autoprefixer])))
        .pipe(cmq())
        .pipe(gulpif(PROD, cleancss({ compatibility: 'ie11' })))
        .pipe(gulpif(!PROD, sourcemaps.write()))
        .pipe(dest(paths.dist.style))
}

export const scripts = () => {
    return src([`${paths.src.js}main.js`])
        .pipe(named())
        .pipe(
            webpack({
                module: {
                    rules: [
                        {
                            test: /\.js$/,
                            use: {
                                loader: 'babel-loader',
                                options: {
                                    presets: ['@babel/preset-env'],
                                },
                            },
                        },
                    ],
                },
                mode: PROD ? 'production' : 'development',
                devtool: !PROD ? 'inline-source-map' : false,
                output: {
                    filename: '[name].js',
                },
            })
        )
        .pipe(dest(paths.dist.js))
}

export const images = () => {
    return src(`${paths.src.img}**/*.{jpg,jpeg,png,svg,gif}`)
        .pipe(
            gulpif(
                PROD,
                imagemin(
                    [
                        imagemin.gifsicle({ interlaced: true }),
                        imagemin.mozjpeg({ quality: 75, progressive: true }),
                        imagemin.optipng({ optimizationLevel: 3 }),
                        imagemin.svgo({
                            plugins: [{ removeViewBox: true }, { cleanupIDs: false }],
                        }),
                    ],
                    {
                        verbose: true,
                    }
                )
            )
        )
        .pipe(dest(paths.dist.img))
}

export const copy = () => {
    return src(['src/**/*', '!src/{img,js,scss}', '!src/{img,js,scss}/**/*']).pipe(dest('dist'))
}

export const htmlfileinclude = () => {
    return src('dist/**/*.html')
        .pipe(
            finclude({
                prefix: '@',
                basepath: '@file',
            })
        )
        .pipe(dest('dist'))
}

export const cleanup = () => del(['dist/**/*'], { force: true })

export const cleanuphtml = () => del(['dist/template-parts'], { force: true })

export const watchChanges = () => {
    watch(`${paths.src.style}**/*.scss`, styles)
    watch(`${paths.src.js}**/*.js`, scripts)
    watch(`${paths.src.img}**/*{jpg, jpeg, png, svg, gif}`, images)
    watch(['src/**/*', '!src/{img,js,scss}', '!src/{img,js,scss}/**/*'], series(copy, htmlfileinclude, cleanuphtml))
}

export const dev = series(
    cleanup,
    parallel(styles, scripts, images, series(copy, htmlfileinclude, cleanuphtml)),
    watchChanges
)

export const build = series(cleanup, parallel(styles, scripts, images, series(copy, htmlfileinclude, cleanuphtml)))

export default dev
