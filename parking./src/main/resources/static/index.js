 const navigate_link =  document.getElementsByClassName("navigate_link") //
 const navigate_button =  document.getElementsByClassName("navigate_button")


    let part1=""
    let part2=""

 for (let i = 0; i < navigate_button.length; i++) {
  navigate_button[i].addEventListener("click", () => {
 
      var url = navigate_link[i].getAttribute('href')
      const parts = url.split("dir/");
      const firstPart = parts[0] + "dir/";
      part1=firstPart
      part2=parts[1]

    // console.log(i + 1);
    // console.log(part1);
    // console.log(part2);
    getLocation()
  });
}
function getLocation() {
  if (navigator.geolocation) {
    navigator.geolocation.getCurrentPosition(showPosition);
  } else {
    console.log("Geolocation is not supported by this browser.")
  }
}

function showPosition(position) {
  var fulCords = `${part1}${position.coords.latitude},${position.coords.longitude}/${part2}`
  console.log(fulCords);
  window.open(fulCords, '_blank');
}




{/* <iframe src="https://www.google.com/maps/embed?+${src}"
update places set src="pb=!1m18!1m12!1m3!1d3989.756106319232!2d37.65430965060593!3d-0.3199674354251008!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x1827b9cbd73ed95f%3A0xefb4ff5ee3ca6fd4!2sChuka%20University%20Gate%20F!5e0!3m2!1sen!2ske!4v1679776947082!5m2!1sen!2ske", position="https://www.google.com/maps/dir/Chuka+University+Gate+F,+Chuka/''/@-0.320181,37.6506304,17z/data=!3m1!4b1!4m17!1m7!3m6!1s0x1827b9cbd73ed95f:0xefb4ff5ee3ca6fd4!2sChuka+University+Gate+F!8m2!3d-0.3199728!4d37.6565037!16s%2Fg%2F11swm_rv0n!4m8!1m1!4e1!1m5!1m1!1s0x1827b9cbd73ed95f:0xefb4ff5ee3ca6fd4!2m2!1d37.6565037!2d-0.3199728" where
id=1
 */}









