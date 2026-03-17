import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { Eye, Sun, Layout, Waves, Target, ChevronRight } from 'lucide-react';
import Navbar from '../components/Navbar';
import UploadCard from '../components/UploadCard';

const FEATURES = [
  {
    Icon: Target,
    label: 'Sharpness & Clarity',
    desc: 'Laplacian variance with subject-weighted 70/30 scoring across edge density and local contrast.'
  },
  {
    Icon: Sun,
    label: 'Exposure Analysis',
    desc: 'Histogram-based highlight & shadow detection with subject-region weighting for accurate exposure state.'
  },
  {
    Icon: Layout,
    label: 'Composition Scoring',
    desc: 'Rule-of-thirds, golden ratio, and subject centroid detection with visual overlay on every image.'
  },
  {
    Icon: Waves,
    label: 'Noise Estimation',
    desc: 'Gaussian residual noise measurement with scene-aware scoring for portrait, landscape and architecture.'
  }
];

const STEPS = [
  { num: '01', label: 'Upload', desc: 'Drop any JPEG, PNG, or WEBP image — up to 10 MB.' },
  { num: '02', label: 'Analyze', desc: 'OpenCV runs 9 parallel analysis modules on your image.' },
  { num: '03', label: 'Review', desc: 'Get your score breakdown, overlays, and improvement tips.' }
];

function LandingPage() {
  const navigate = useNavigate();
  const [isLoading, setIsLoading] = useState(false);
  const [progress, setProgress] = useState(0);
  const [preview, setPreview] = useState('');
  const [error, setError] = useState('');

  const handleUpload = async (file) => {
    const { uploadImage } = await import('../services/api');
    setError('');
    setIsLoading(true);
    setProgress(5);
    const localUrl = URL.createObjectURL(file);
    setPreview(localUrl);

    try {
      const result = await uploadImage(file, (event) => {
        if (!event.total) return;
        setProgress(Math.max(8, Math.round((event.loaded * 100) / event.total)));
      });
      setProgress(100);
      navigate(`/dashboard/${result.data.id}`, {
        state: { analysis: result.data, previewUrl: localUrl }
      });
    } catch (apiError) {
      setError(apiError.response?.data?.message || 'Upload failed. Please try another image.');
    } finally {
      setTimeout(() => { setIsLoading(false); setProgress(0); }, 400);
    }
  };

  return (
    <div className="min-h-screen">
      <Navbar />

      {/* ── Hero ── */}
      <section className="relative overflow-hidden">
        <div className="hero-grid-bg pointer-events-none absolute inset-0 opacity-90" />

        <div className="mx-auto max-w-7xl px-6 pb-20 pt-16">
          {/* Pill badge */}
          <div className="mb-8 flex justify-center">
            <span className="inline-flex items-center gap-2 rounded-full border border-brand-accent/20 bg-brand-accent/5 px-4 py-1.5 text-xs font-medium text-brand-accentLight">
              <span className="h-1.5 w-1.5 animate-pulse rounded-full bg-brand-accent" />
              Powered by OpenCV &amp; Spring Boot
            </span>
          </div>

          {/* Headline */}
          <div className="mb-12 text-center">
            <h1 className="text-5xl font-extrabold leading-[1.08] tracking-tight text-white sm:text-6xl lg:text-7xl">
              Analyze. Understand.{' '}
              <span className="gradient-text">Perfect.</span>
            </h1>
            <p className="mx-auto mt-6 max-w-2xl text-base leading-relaxed text-brand-mutedLight sm:text-lg">
              Upload any photo and receive a full forensic breakdown — sharpness, exposure, composition,
              symmetry, noise, and scene-aware improvement suggestions in seconds.
            </p>
          </div>

          {/* Upload + Preview */}
          <div className="mx-auto grid max-w-5xl gap-6 lg:grid-cols-2">
            <div className="space-y-4">
              <UploadCard onUpload={handleUpload} isLoading={isLoading} progress={progress} />
              {error && (
                <div className="flex items-start gap-3 rounded-xl border border-red-500/20 bg-red-500/8 px-4 py-3 text-sm text-red-300">
                  <span className="shrink-0 font-bold">⚠</span>
                  {error}
                </div>
              )}
            </div>

            <div className="space-y-4">
              {preview ? (
                <div className="glass-card overflow-hidden p-1">
                  <img
                    src={preview}
                    alt="Selected"
                    className="h-full max-h-72 w-full rounded-xl object-cover"
                  />
                </div>
              ) : (
                <div className="glass-card flex h-72 flex-col items-center justify-center gap-3 rounded-2xl">
                  <Eye className="h-10 w-10 text-brand-muted/30" />
                  <p className="text-sm text-brand-muted">Image preview will appear here</p>
                </div>
              )}

              <div className="glass-card p-5">
                <p className="mb-3 text-[11px] font-semibold uppercase tracking-widest text-brand-muted">
                  Analysis Coverage
                </p>
                <ul className="space-y-2">
                  {[
                    'Sharpness · edge density · local contrast',
                    'Exposure balance · highlight & shadow clipping',
                    'Composition · rule-of-thirds · golden ratio',
                    'Symmetry · SSIM vertical axis measurement',
                    'Noise estimation · Gaussian residual method',
                    'Face detection · quality & lighting score',
                    'Scene awareness · portrait / landscape / architecture'
                  ].map((item) => (
                    <li key={item} className="flex items-center gap-2.5 text-xs text-brand-mutedLight">
                      <ChevronRight className="h-3 w-3 shrink-0 text-brand-accent" />
                      {item}
                    </li>
                  ))}
                </ul>
              </div>
            </div>
          </div>
        </div>
      </section>

      {/* ── How it works ── */}
      <section className="border-t border-white/5 py-16">
        <div className="mx-auto max-w-7xl px-6">
          <p className="mb-10 text-center text-[11px] font-semibold uppercase tracking-widest text-brand-muted">
            How it works
          </p>
          <div className="grid gap-6 sm:grid-cols-3">
            {STEPS.map(({ num, label, desc }) => (
              <div key={num} className="glass-card p-6">
                <p className="mb-3 font-mono text-3xl font-black text-brand-primary/30">{num}</p>
                <p className="text-sm font-semibold text-white">{label}</p>
                <p className="mt-1.5 text-xs leading-relaxed text-brand-muted">{desc}</p>
              </div>
            ))}
          </div>
        </div>
      </section>

      {/* ── Features strip ── */}
      <section className="border-t border-white/5 bg-brand-panel/40 py-16">
        <div className="mx-auto max-w-7xl px-6">
          <p className="mb-10 text-center text-[11px] font-semibold uppercase tracking-widest text-brand-muted">
            What gets analyzed
          </p>
          <div className="grid gap-5 sm:grid-cols-2 lg:grid-cols-4">
            {FEATURES.map(({ Icon, label, desc }) => (
              <div
                key={label}
                className="glass-card group p-5 transition-all duration-200 hover:-translate-y-1 hover:border-white/10"
              >
                <div className="mb-4 inline-flex h-10 w-10 items-center justify-center rounded-xl bg-gradient-to-br from-brand-primary/15 to-brand-accent/15 text-brand-accentLight ring-1 ring-white/8">
                  <Icon className="h-5 w-5" />
                </div>
                <p className="text-sm font-semibold text-white">{label}</p>
                <p className="mt-1.5 text-xs leading-relaxed text-brand-muted">{desc}</p>
              </div>
            ))}
          </div>
        </div>
      </section>
    </div>
  );
}

export default LandingPage;
