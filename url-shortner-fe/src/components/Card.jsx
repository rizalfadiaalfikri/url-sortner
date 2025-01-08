import PropTypes from "prop-types";

function Card({title, desc}) {
  return (
    <div
    //   initial={{ opacity: 0, y: 120 }}
    //   whileInView={{
    //     opacity: 1,
    //     y: 0,
    //   }}
    //   viewport={{ once: true }}
    //   transition={{ duration: 0.5 }}
      className="shadow-md shadow-slate-400 border flex flex-col px-4 py-8  gap-3 rounded-sm"
    >
      <h1 className="text-slate-900 text-xl font-bold ">{title}</h1>
      <p className="text-slate-700 text-sm"> {desc}</p>
    </div>
  );
}

Card.propTypes = {
  title: PropTypes.string.isRequired,
  desc: PropTypes.string.isRequired,
};

export default Card;
