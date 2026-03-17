const LEGEND = [
  { color: 'rgb(125,211,252)', style: 'solid', label: 'Rule of Thirds' },
  { color: 'rgb(196,181,253)', style: 'dashed', label: 'Golden Ratio' },
  { color: 'rgb(110,231,183)', style: 'solid', label: 'Symmetry Axis' },
  { color: 'rgb(103,232,249)', style: 'solid', label: 'Subject Region' },
  { color: 'rgb(232,121,249)', style: 'solid', label: 'Centroid' },
  { color: 'rgb(252,211,77)', style: 'solid', label: 'Face Box' }
];

function CompositionOverlayCard({ src, analysis }) {
  const width = analysis?.imageWidth || 1;
  const height = analysis?.imageHeight || 1;

  const subjectCentroidStyle = {
    left: `${((analysis?.subjectCentroidX || width / 2) / width) * 100}%`,
    top: `${((analysis?.subjectCentroidY || height / 2) / height) * 100}%`
  };

  const subjectRegionStyle = {
    left: `${((analysis?.subjectRegionX || 0) / width) * 100}%`,
    top: `${((analysis?.subjectRegionY || 0) / height) * 100}%`,
    width: `${((analysis?.subjectRegionWidth || width) / width) * 100}%`,
    height: `${((analysis?.subjectRegionHeight || height) / height) * 100}%`
  };

  const faceBoxStyle = {
    left: `${((analysis?.faceX || 0) / width) * 100}%`,
    top: `${((analysis?.faceY || 0) / height) * 100}%`,
    width: `${((analysis?.faceWidth || 0) / width) * 100}%`,
    height: `${((analysis?.faceHeight || 0) / height) * 100}%`
  };

  const symmetryAxisLeft = `${((analysis?.symmetryAxisX || width / 2) / width) * 100}%`;

  return (
    <div className="glass-card animate-rise p-6">
      <div className="mb-1 flex items-start justify-between gap-4">
        <div>
          <h3 className="text-sm font-semibold text-white">Composition Analysis</h3>
          <p className="mt-0.5 text-xs text-brand-muted">
            {analysis?.compositionFeedback || 'Visual overlays show framing and subject positioning.'}
          </p>
        </div>
      </div>

      {/* Overlay image */}
      <div className="relative mt-4 overflow-hidden rounded-xl border border-white/8 bg-brand-panelSoft">
        {src ? (
          <>
            <img src={src} alt="composition preview" className="h-80 w-full object-cover" />
            <div className="pointer-events-none absolute inset-0">
              {/* Rule-of-thirds — sky blue */}
              <div className="absolute left-1/3 top-0 h-full w-px bg-sky-300/50" />
              <div className="absolute left-2/3 top-0 h-full w-px bg-sky-300/50" />
              <div className="absolute left-0 top-1/3 h-px w-full bg-sky-300/50" />
              <div className="absolute left-0 top-2/3 h-px w-full bg-sky-300/50" />

              {/* Golden ratio — violet dashed */}
              <div className="absolute left-[38.2%] top-0 h-full w-px border-l border-dashed border-violet-400/55" />
              <div className="absolute left-[61.8%] top-0 h-full w-px border-l border-dashed border-violet-400/55" />
              <div className="absolute left-0 top-[38.2%] h-px w-full border-t border-dashed border-violet-400/55" />
              <div className="absolute left-0 top-[61.8%] h-px w-full border-t border-dashed border-violet-400/55" />

              {/* Symmetry axis — emerald */}
              <div
                className="absolute top-0 h-full w-px bg-emerald-400/70"
                style={{ left: symmetryAxisLeft }}
              />

              {/* Subject region — cyan border */}
              <div
                className="absolute rounded-md border border-cyan-300/80"
                style={subjectRegionStyle}
              />

              {/* Subject centroid — fuchsia dot */}
              <div
                className="absolute h-3 w-3 -translate-x-1/2 -translate-y-1/2 rounded-full border-2 border-white bg-fuchsia-400 shadow-[0_0_12px_rgba(217,70,239,0.85)]"
                style={subjectCentroidStyle}
              />

              {/* Face bounding box — amber */}
              {analysis?.faceDetected && (analysis?.faceWidth || 0) > 0 && (
                <div className="absolute border-2 border-amber-300/90" style={faceBoxStyle} />
              )}
            </div>
          </>
        ) : (
          <div className="flex h-80 items-center justify-center text-xs text-brand-muted">
            Upload an image to see composition overlays.
          </div>
        )}
      </div>

      {/* Legend */}
      <div className="mt-3 flex flex-wrap gap-x-4 gap-y-1.5">
        {LEGEND.map(({ color, style, label }) => (
          <div key={label} className="flex items-center gap-1.5">
            <span
              className="inline-block h-2.5 w-5 rounded-sm"
              style={{
                backgroundColor: style === 'solid' ? color : 'transparent',
                border: style === 'dashed' ? `1.5px dashed ${color}` : 'none',
                opacity: 0.85
              }}
            />
            <span className="text-[10px] text-brand-muted">{label}</span>
          </div>
        ))}
      </div>

      {/* Info tiles */}
      <div className="mt-4 grid gap-3 sm:grid-cols-3">
        <div className="metric-tile">
          <p className="text-[10px] text-brand-muted">Subject Placement</p>
          <p className="mt-1 text-sm font-semibold text-brand-text">{analysis?.subjectPlacement || 'N/A'}</p>
        </div>
        <div className="metric-tile">
          <p className="text-[10px] text-brand-muted">Rule-of-Thirds</p>
          <p className="mt-1 text-sm font-semibold text-brand-text">{Math.round(analysis?.ruleOfThirdsAlignmentScore || 0)}/100</p>
        </div>
        <div className="metric-tile">
          <p className="text-[10px] text-brand-muted">Golden Ratio</p>
          <p className="mt-1 text-sm font-semibold text-brand-text">{Math.round(analysis?.goldenRatioScore || 0)}/100</p>
        </div>
      </div>
    </div>
  );
}

export default CompositionOverlayCard;
