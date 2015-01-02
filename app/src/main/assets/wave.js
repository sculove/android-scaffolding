function Wave() {
	/** The current dimensions of the screen (updated on resize) */
	var WIDTH = window.innerWidth;
	var HEIGHT = window.innerHeight;
	/** Wave settings */
	var DENSITY = .75;
	var FRICTION = 1.14;
	var MOUSE_PULL = 0.09; // The strength at which the mouse pulls particles within the AOE
	var AOE = 200; // Area of effect for mouse pull

	var DETAIL = Math.round(WIDTH / 60); // The number of particles used to build up the wave

	var TWITCH_INTERVAL = 1000; // The interval between random impulses being inserted into the wave to keep it moving

	var ms = {
		x: 0,
		y: 0
	}; // Mouse speed
	var mp = {
		x: 0,
		y: 0
	}; // Mouse position

	var canvas, context, particles, bubbles, tweets;

	var timeUpdateInterval, twitchInterval;

	var VALUE = 0.3;

	/**
	 * Constructor.
	 */
	this.Initialize = function(canvasID) {
		canvas = document.getElementById(canvasID);
		if (canvas && canvas.getContext) {
			context = canvas.getContext('2d');

			particles = [];
			tweets = [];

			// Generate our wave particles
			for (var i = 0; i < DETAIL + 1; i++) {
				particles.push({
					x: WIDTH / (DETAIL - 4) * (i - 2), // Pad by two particles on each side
					y: HEIGHT * VALUE,
					original: {
						x: 0,
						y: HEIGHT * VALUE
					},
					velocity: {
						x: 0,
						y: Math.random() * 3
					}, // Random for some initial movement in the wave
					force: {
						x: 0,
						y: 0
					},
					mass: 10
				});
			}
			// $(window).resize(ResizeCanvas);

			timeUpdateInterval = setInterval(TimeUpdate, 40);
			twitchInterval = setInterval(Twitch, TWITCH_INTERVAL);

			ResizeCanvas(0.2);

		}
	};


	/**
	 * Inserts a random impulse to keep the wave moving.
	 * Impulses are only inserted if the mouse is not making
	 * quick movements.
	 */
	function Twitch() {
		if (ms.x < 6 || ms.y < 6) {
			var forceRange = 5; // -value to +value
			InsertImpulse(Math.random() * WIDTH, (Math.random() * (forceRange * 2) - forceRange));
		}
	}

	/**
	 * Inserts an impulse in the wave at a specific position.
	 *
	 * @param positionX the x coordinate where the impulse
	 * should be inserted
	 * @param forceY the force to insert
	 */
	function InsertImpulse(positionX, forceY) {
		var particle = particles[Math.round(positionX / WIDTH * particles.length)];

		if (particle) {
			particle.force.y += forceY;
		}
	}

	/**
	 *
	 */
	function TimeUpdate(e) {

		var gradientFill = context.createLinearGradient(WIDTH * .5, HEIGHT * .2, WIDTH * .5, HEIGHT);
		gradientFill.addColorStop(0, '#3E2723');
		// gradientFill.addColorStop(1,'rgba(0,200,250,0)');
		gradientFill.addColorStop(1, '#EFEBE9');

		context.clearRect(0, 0, WIDTH, HEIGHT);
		context.fillStyle = gradientFill;
		context.beginPath();
		context.moveTo(particles[0].x, particles[0].y);

		var len = particles.length;
		var i;

		var current, previous, next;

		for (i = 0; i < len; i++) {
			current = particles[i];
			previous = particles[i - 1];
			next = particles[i + 1];

			if (previous && next) {

				var forceY = 0;

				forceY += -DENSITY * (previous.y - current.y);
				forceY += DENSITY * (current.y - next.y);
				forceY += DENSITY / 15 * (current.y - current.original.y);

				current.velocity.y += -(forceY / current.mass) + current.force.y;
				current.velocity.y /= FRICTION;
				current.force.y /= FRICTION;
				current.y += current.velocity.y;

				var distance = DistanceBetween(mp, current);

				if (distance < AOE) {
					var distance = DistanceBetween(mp, {
						x: current.original.x,
						y: current.original.y
					});

					ms.x = ms.x * .98;
					ms.y = ms.y * .98;

					current.force.y += (MOUSE_PULL * (1 - (distance / AOE))) * ms.y;
				}

				// cx, cy, ax, ay
				context.quadraticCurveTo(previous.x, previous.y, previous.x + (current.x - previous.x) / 2, previous.y + (current.y - previous.y) / 2);
			}

		}
		context.lineTo(particles[particles.length - 1].x, particles[particles.length - 1].y);
		context.lineTo(WIDTH, HEIGHT);
		context.lineTo(0, HEIGHT);
		context.lineTo(particles[0].x, particles[0].y);
		context.fill();

		context.fillStyle = "#rgba(0,200,255,0)";
		context.beginPath();
		context.fill();
	}


	/**
	 *
	 */
	function ResizeCanvas(percent) {
		WIDTH = window.innerWidth;
		HEIGHT = window.innerHeight;

		VALUE = 1 - percent;
		canvas.width = WIDTH;
		canvas.height = HEIGHT;

		for (var i = 0; i < DETAIL + 1; i++) {
			particles[i].x = WIDTH / (DETAIL - 4) * (i - 2);
			particles[i].y = HEIGHT * VALUE;
			particles[i].original.x = particles[i].x;
			particles[i].original.y = particles[i].y;
		}
	}

	/**
	 *
	 */
	function DistanceBetween(p1, p2) {
		var dx = p2.x - p1.x;
		var dy = p2.y - p1.y;
		return Math.sqrt(dx * dx + dy * dy);
	}

}
var wave = new Wave();
wave.Initialize('water');