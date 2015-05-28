var gulp = require('gulp');
var connect = require('gulp-connect');

gulp.task('connect', function() {
  connect.server({
    livereload: true
  });
});
 
gulp.task('watch', function () {
  gulp.watch(['./*.html'], function(){
  	 gulp.src('./*.html')
    .pipe(connect.reload());
  });
});

gulp.task('default', ['connect', 'watch']);
